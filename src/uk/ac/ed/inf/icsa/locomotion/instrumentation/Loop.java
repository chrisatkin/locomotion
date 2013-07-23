package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LocomotionError;
import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

class Loop {
	private final String id;
	private Class<? extends Trace> store;
	private TraceConfiguration traceConfiguration;
	private final Map<Integer, Trace> iterations;
	
	public Loop(final String id, Class<? extends Trace> store, TraceConfiguration traceConfiguration) {
		this.id = id;
		this.store = store;
		this.traceConfiguration = traceConfiguration;
		this.iterations = new HashMap<Integer, Trace>();
	}
	
	public void addIterationAccess(final int iteration, final int index, final int arrayId, final Kind kind) throws LoopDependencyException {
		if (!iterations.containsKey(iteration))
			try {
				try {
					iterations.put((Integer) iteration, store.getDeclaredConstructor(TraceConfiguration.class).newInstance(traceConfiguration));
				} catch (IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			} catch (InstantiationException | IllegalAccessException exc) {
				exc.printStackTrace();
				throw new LocomotionError(store + " does not implement the correct interface");
			}
		
		Trace currentAccesses = (Trace) iterations.get(iteration);
		Access access = new Access(arrayId, index, currentAccesses.size(), kind);
		
		// check for intra-iteration dependency
		if (currentAccesses.contains(access))
			System.out.println("inter-iteration dependency found for " + access.toString());
		
		// check for inter-iteration dependency
		for (Map.Entry<Integer, Trace> otherIterations: iterations().entrySet()) {
			Integer otherIterationNumber = otherIterations.getKey();
			Trace otherIterationAccesses = otherIterations.getValue();
			
			if (otherIterationNumber == iteration)
				continue;
			
			if (otherIterationAccesses.contains(access))
				throw new LoopDependencyException(access, iteration, otherIterationNumber);
		}
		
		((Trace) iterations.get(iteration)).add(access);
	}
	
	public Map<Integer, Trace> iterations() {
		return iterations;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for(Map.Entry<Integer, ? extends Trace> entry: iterations.entrySet()) {
			Integer iterationId = entry.getKey();
			Trace iteration = entry.getValue();
			
			string.append("iteration=").append(iterationId).append(iteration.toString()).append("\n");
		}
		
		return string.toString();
	}
	
	public String getId() {
		return id;
	}
}