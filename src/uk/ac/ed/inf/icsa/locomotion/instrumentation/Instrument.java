package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;

public class Instrument {
	private final Configuration configuration;
	private Map<String, Loop> store;
	private List<LoopDependencyException> dependencies;
	private long startTime;
	private long endTime;
	
	public Instrument(Configuration configuration) {
		this.store = new HashMap<>();
		this.dependencies = new LinkedList<>();
		this.configuration = configuration;
	}
	
	public void clear() {
		this.store = new HashMap<>();
		this.dependencies = new LinkedList<>();
	}
	
	public <T> void instrumentArrayLoad(final T[] array, final int index, final int loopIterator, final String loopId) {
		//System.out.println("load array=" + array.hashCode() + " index=" + index + " iterator=" + loopIterator + " id=" + loopId);
		if (!configuration.instrumentationEnabled())
			return;
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId, configuration.getLoopStoreClass(), configuration.getLoopStoreConfiguration()));
		
		try {
			store.get(loopId).addIterationAccess(loopIterator, index, array.hashCode(), Kind.Load);
		} catch (LoopDependencyException e) {
			dependencies.add(e);
		}
	}	
	
	public <T> void instrumentArrayWrite(final T[] array, final int index, final T value, final int loopIterator, final String loopId) {
		//System.out.println("store array=" + array.hashCode() + " index=" + index  +" value=" + value + " iterator=" + loopIterator + " id=" + loopId);
		if (!configuration.instrumentationEnabled())
			return;
		
		if (!store.containsKey(loopId))
			store.put(loopId, new Loop(loopId, configuration.getLoopStoreClass(), configuration.getLoopStoreConfiguration()));
		
		try {
			store.get(loopId).addIterationAccess(loopIterator, index, array.hashCode(), Kind.Store);
		} catch (LoopDependencyException e) {
			dependencies.add(e);
		}
		
		if (configuration.reportMemory())
			reportMemory();
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
	
	public Map<String, Loop> getLoopsOtherThan(String otherThan) {
		HashMap<String, Loop> storeCopy = new HashMap<String, Loop>(store);
		storeCopy.remove(otherThan);
		
		return storeCopy;
	}
	
	public String report() {
		return dependencies.toString();
	}
	
	public List<LoopDependencyException> getDepencendies() {
		return dependencies;
	}
	
	public void reportMemory() {
		configuration.getOutput().put("memory=" + getTraceMemoryUsage());
	}

	public long getTraceMemoryUsage() {
		long result = 0;
		
		for (Map.Entry<String, Loop> entry: store.entrySet()) {
			result += entry.getValue().getMemoryUsage();
		}
		
		return result;
	}
}