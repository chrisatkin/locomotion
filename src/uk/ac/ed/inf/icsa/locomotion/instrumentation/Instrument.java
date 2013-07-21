package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import static uk.ac.ed.inf.icsa.locomotion.utilities.DebugUtilities.noop;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;

public class Instrument {
	private final Configuration configuration;
	private final Map<Integer, Loop> store;
	private List<LoopDependencyException> dependencies;
	private long startTime;
	private long endTime;
	
	public Instrument(Configuration configuration) {
		this.store = new HashMap<Integer, Loop>();
		this.dependencies = new LinkedList<LoopDependencyException>();
		this.configuration = configuration;
	}
	
	public <T> void instrumentArrayLoad(final T[] array, final int index, final int loopIterator, final int loopId) {
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
	
	public <T> void instrumentArrayWrite(final T[] array, final int index, final T value, final int loopIterator, final int loopId) {
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
		return dependencies.toString();
	}
	
	public String dependencyReport() {
		throw new Error();
	}
}