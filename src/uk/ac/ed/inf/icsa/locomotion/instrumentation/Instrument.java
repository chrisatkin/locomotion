package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static uk.ac.ed.inf.icsa.locomotion.utilities.DebugUtilities.noop;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class Instrument {
	private static class Loop {
		private final int id;
		private final Map<Integer, Set<Access>> iterations;
		
		public Loop(final int id) {
			this.id = id;
			this.iterations = new HashMap<Integer, Set<Access>>();
		}
		
		public void addIterationAccess(final int iteration, final int index, final int arrayId, final Kind kind) {
			if (!iterations.containsKey(iteration)) 
				iterations.put(iteration, new HashSet<Access>());
			
			Set<Access> currentAccesses = iterations.get(iteration);
			Access access = new Access(arrayId, index, currentAccesses.size(), kind);
			
			// check for intra-iteration dependency
			if (currentAccesses.contains(access))
				System.out.println("inter-iteration dependency found for " + access.toString());
			
			// check for inter-iteration dependency
			for (Map.Entry<Integer, Set<Access>> otherIterations: iterations(iteration).entrySet()) {
				Integer otherIterationNumber = otherIterations.getKey();
				Set<Access> otherIterationAccesses = otherIterations.getValue();
				
				if (otherIterationAccesses.contains(access))
					System.out.println("intra-iteration dependency found for " + access.toString());
			}
			
			iterations.get(iteration).add(access);
		}
		
		public Map<Integer, Set<Access>> iterations() {
			return iterations;
		}
		
		public Map<Integer, Set<Access>> iterations(int otherThan) {
			Map<Integer, Set<Access>> iterationsCopy = new HashMap<Integer, Set<Access>>(iterations);
			iterationsCopy.remove((Integer) otherThan);
			
			return iterationsCopy;
		}
		
		public String toString() {
			StringBuilder string = new StringBuilder();
			
			for(Map.Entry<Integer, Set<Access>> entry: iterations.entrySet()) {
				Integer iterationId = entry.getKey();
				Set<Access> iteration = entry.getValue();
				
				string.append("iteration=").append(iterationId).append(iteration.toString()).append("\n");
			}
			
			return string.toString();
		}
	}
	
	private static class Access implements Comparable<Access> {
		private final Kind kind;
		private final int arrayId;
		private final int index;
		private final int number; // this is the i'th access this iteration
		
		public Access(final int arrayId, final int index, final int number, final Kind kind) {
			this.kind = kind;
			this.number = number;
			this.arrayId = arrayId;
			this.index = index;
		}
		
		public String toString() {
			return new StringBuilder().append("kind=").append(kind.toString()).append(" arrayId=").append(arrayId).append(" index=").append(index).append(" number=").append(number).toString();
		}
		
		@Override
		public int hashCode() {
			Long l = Long.parseLong("" + arrayId + index + kind.ordinal());
			return l.hashCode();
		}
		
		public int getNumber() {
			return number;
		}

		@Override
		public int compareTo(Access other) {			
			if (this.getNumber() > other.getNumber())
				return +1;
			
			if (this.getNumber() < other.getNumber())
				return -1;
			
			return 0;
		}
		
		public boolean equals(Access other) {
			return (this.arrayId == other.arrayId) && (this.index == other.index);
		}
	}
	
	private final Configuration configuration;
	private final Map<Integer, Loop> store;
	private long startTime;
	private long endTime;
	
	public Instrument(Configuration configuration) {
		this.store = new HashMap<Integer, Loop>();
		this.configuration = configuration;
	}
	
	public <T> void instrumentArrayLoad(final T[] array, final int index, final int loopIterator, final int loopId) {
		//System.out.println("load array=" + array.hashCode() + " index=" + index + " iterator=" + loopIterator + " id=" + loopId);
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId));
		
		store.get(loopId).addIterationAccess(loopIterator, index, array.hashCode(), Kind.Load);
	}	
	
	public <T> void instrumentArrayWrite(final T[] array, final int index, final T value, final int loopIterator, final int loopId) {
		//System.out.println("store array=" + array.hashCode() + " index=" + index  +" value=" + value + " iterator=" + loopIterator + " id=" + loopId);
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId));
		
		store.get(loopId).addIterationAccess(loopIterator, index, array.hashCode(), Kind.Store);
	}
	
	public void recordStart() {
		this.startTime = System.nanoTime();
	}
	
	public void recordEnd() {
		this.endTime = System.nanoTime();
	}
	
	public long getTimeDifference() {
		return endTime - startTime;
	}
	
	public Map<Integer, Loop> getLoopsOtherThan(int otherThan) {
		HashMap<Integer, Loop> storeCopy = new HashMap<Integer, Loop>(store);
		storeCopy.remove((Integer) otherThan);
		
		return storeCopy;
	}
	
	public String report() {
		StringBuilder string = new StringBuilder();
		
		for (Map.Entry<Integer, Loop> entry: store.entrySet()) {
			Integer loopId = entry.getKey();
			Loop loop = entry.getValue();
			
			string.append("loop=").append(loopId).append("\n").append(loop.toString());
		}
		
		return string.toString();
	}
	
	public String dependencyReport() {
		throw new Error();
	}
}