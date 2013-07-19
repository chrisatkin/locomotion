package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static uk.ac.ed.inf.icsa.locomotion.utilities.DebugUtilities.noop;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class Instrument {
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
		if (!configuration.instrumentationEnabled())
			return;
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId, configuration.getLoopStoreClass(), configuration.getLoopStoreConfiguration()));
		
		store.get(loopId).addIterationAccess(loopIterator, index, array.hashCode(), Kind.Load);
	}	
	
	public <T> void instrumentArrayWrite(final T[] array, final int index, final T value, final int loopIterator, final int loopId) {
		//System.out.println("store array=" + array.hashCode() + " index=" + index  +" value=" + value + " iterator=" + loopIterator + " id=" + loopId);
		if (!configuration.instrumentationEnabled())
			return;
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId, configuration.getLoopStoreClass(), configuration.getLoopStoreConfiguration()));
		
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