package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.HashMap;
import java.util.Map;

import org.github.jamm.MemoryMeter;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

class Loop {
	private static class Tuple<T> {
		public T read;
		public T write;
		
		public String toString() {
			StringBuilder string = new StringBuilder();
			string.append("read=").append(read.toString()).append(" write=").append(write.toString());
			return string.toString();
		}
		
		public long getMemoryUsage() {
			MemoryMeter m = new MemoryMeter();
			return m.measureDeep(read) + m.measureDeep(write);
		}
	}
	
	private final String id;
	private Class<? extends Trace> store;
	private TraceConfiguration traceConfiguration;
	private final Map<Integer, Tuple<Trace>> iterations;
	
	public Loop(final String id, Class<? extends Trace> store, TraceConfiguration traceConfiguration) {
		this.id = id;
		this.store = store;
		this.traceConfiguration = traceConfiguration;
		this.iterations = new HashMap<Integer, Tuple<Trace>>();
	}
	
	public void addIterationAccess(final int iteration, final int index, final int arrayId, final AccessKind kind) throws LoopDependencyException {
		if (!iterations.containsKey(iteration))
			try {
				Tuple<Trace> t = new Tuple<Trace>();
				t.read = store.getDeclaredConstructor(TraceConfiguration.class).newInstance(traceConfiguration);
				t.write = store.getDeclaredConstructor(TraceConfiguration.class).newInstance(traceConfiguration);
				
				iterations.put((Integer) iteration, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		//Trace currentAccesses = (Trace) iterations.get(iteration);
		Tuple<Trace> t = iterations.get(iteration);
		Access access = new Access(arrayId, index/*, t.read.size() + t.write.size()*/, kind);
		
		// check for inter-iteration dependency
		for (Map.Entry<Integer, Tuple<Trace>> otherIterations: iterations.entrySet()) {
			Integer otherIterationNumber = otherIterations.getKey();
			Tuple<Trace> otherIterationTuple = otherIterations.getValue();
			//Trace otherIterationAccesses = otherIterations.getValue();
			
			if (otherIterationNumber == iteration)
				continue;
			
			switch (kind) {
				case Store:
					if ((otherIterationTuple.read).contains(access)) {
						// read-write dependency
						throw new LoopDependencyException(access, iteration, DependencyKind.ReadWrite);
					}
					
					if ((otherIterationTuple.write).contains(access)) {
						// write-write dependency
						throw new LoopDependencyException(access, iteration, DependencyKind.WriteWrite);
					}
				break;
				
				case Load:
					if ((otherIterationTuple.read).contains(access)) {
						// read-read dependency, do nothing
						;
					}
					
					if (((Trace) otherIterationTuple.write).contains(access)) {
						// write-read dependency
						throw new LoopDependencyException(access, iteration, DependencyKind.WriteRead);
					}
				
				break;
			}
		}
		
		// add the access to the right store
		if (kind == AccessKind.Load)
			iterations.get(iteration).read.add(access);
		else
			iterations.get(iteration).write.add(access);
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for(Map.Entry<Integer, Tuple<Trace>> entry: iterations.entrySet()) {
			Integer iterationId = entry.getKey();
			Tuple<Trace> iteration = entry.getValue();
			
			string.append("iteration=").append(iterationId).append(iteration.toString()).append("\n");
		}
		
		return string.toString();
	}
	
	public long getMemoryUsage() {
		long result = 0;
		
		for (Map.Entry<Integer, Tuple<Trace>> entry: iterations.entrySet()) {
			Tuple<Trace> trace = entry.getValue();
			
			result += trace.getMemoryUsage();
		}
		
		return result;
	}
	
	public String getId() {
		return id;
	}
}