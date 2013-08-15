package uk.ac.ed.inf.icsa.locomotion.instrumentation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.github.jamm.MemoryMeter;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LoopDependencyException;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.Trace;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.storage.TraceConfiguration;

public final class Instrumentation {
	
	private static enum LoopState {
		InLoop,
		OutLoop
	}
	
	private static enum IterationState {
		InIteration,
		OutIteration,
	}
	
	static {
		i = null;
	}
	
	public static void setConfiguration(Configuration c) {
		i = new Instrumentation(c);
	}
	
	public static void startLoop(String id) {
		i.enterLoop(id);
	}
	
	public static void endLoop() {
		i.exitLoop();
	}
	
	public static void startIteration(int number) {
		i.enterIteration(number);
	}
	
	public static void endIteration() {
		i.exitIteration();
	}
	
	public static <T> T load(T[] array, int index) {
		return i.arrayLoad(array, index);
	}
	
	public static int load(int[] array, int index) {
		return i.arrayLoad(array, index);
	}
	
	public static <T> T[] store(T[] array, int index, T value) {
		return i.arrayStore(array, index, value);
	}
	
	public static int[] store(int[] array, int index, int value) {
		return i.arrayStore(array, index, value);
	}
	
	public static List<LoopDependencyException> dependencies() {
		return i.listDependencies();
	}
	
	public static long memoryUsage() {
		return i.getMemoryUsage();
	}
	
	public static void clean() {
		i._flush();
	}
	
	private static Instrumentation i;
	private static Configuration config;
	
	private Configuration _config;
	private LoopState loopState;
	private IterationState iterationState;
	private String currentLoopId;
	private int currentIterationNumber;
	private Trace loads;
	private Trace stores;
	private Set<Access> currentIterationAccesses;
	private List<LoopDependencyException> dependencies;
	private boolean dependencyDetected;

	public Instrumentation(Configuration config) {
		this._config = config;
		this.loopState = LoopState.OutLoop;
		this.iterationState = IterationState.OutIteration;
		this.currentIterationNumber = 0;
		this.currentLoopId = null;
		this.dependencyDetected = false;
		
		currentIterationAccesses = new HashSet<>();
		dependencies = new LinkedList<>();
		
		try { _create_stores(); }
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void enterLoop(String id) {
		if  (loopState == LoopState.InLoop)
			throw new IllegalStateException("cannot enter loop whilst already in loop " + currentLoopId);
		
		loopState = LoopState.InLoop;
		currentLoopId = id;
	}
	
	private void enterIteration(int number) {
		if (loopState != LoopState.InLoop)
			throw new IllegalStateException("cannot enter iteration whilst not in loop");
		
		if (iterationState != IterationState.OutIteration)
			throw new IllegalStateException("cannot enter iteration whilst in iteration");
		
		iterationState = IterationState.InIteration;
		currentIterationNumber = number;
	}
	
	private void exitLoop() {
		if (loopState == LoopState.OutLoop)
			throw new IllegalStateException("cannot leave loop when not in loop");
		
		//_flushAfterLoop();
		
		loopState = LoopState.OutLoop;
		currentLoopId = null;
	}
	
	private void exitIteration() {
		if (loopState != LoopState.InLoop)
			throw new IllegalStateException("cannot leave iteration whist not in loop");
		
		if (iterationState != IterationState.InIteration)
			throw new IllegalStateException("cannot leave iteration whilst not in iteration");
		
		_flushAfterIteration();
		
		iterationState = IterationState.OutIteration;
		currentIterationNumber = 0;
	}
	
	private <T> T arrayLoad(T[] array, int index) {
		Access a = new Access(array.hashCode(), index, AccessKind.Load);
		
		if (_config.instrumentationEnabled() && !dependencyDetected) {
			if (stores.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.WriteRead));
				dependencyDetected = true;
			}
			
			currentIterationAccesses.add(a);
		}
		
		return array[index];
	}
	
	private int arrayLoad(int[] array, int index) {
		Access a = new Access(array.hashCode(), index, AccessKind.Load);
		
		if (_config.instrumentationEnabled() && !dependencyDetected) {
			if (stores.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.WriteRead));
				dependencyDetected = true;
			}
			
			currentIterationAccesses.add(a);
		}
		
		return array[index];
	}
	
	private <T> T[] arrayStore(T[] array, int index, T value) {
		Access a = new Access(array.hashCode(), index, AccessKind.Store);
		
		if (_config.instrumentationEnabled() && !dependencyDetected) {
			if (stores.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.WriteWrite));
				dependencyDetected = true;
			}
			
			if (loads.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.ReadWrite));
				dependencyDetected = true;
			}
			
			currentIterationAccesses.add(a);
		}
		
		array[index] = value;
		return array;
	}
	
	private int[] arrayStore(int[] array, int index, int value) {
		Access a = new Access(array.hashCode(), index, AccessKind.Store);
		
		if (_config.instrumentationEnabled() && !dependencyDetected) {
			if (stores.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.WriteWrite));
				dependencyDetected = true;
			}
			
			if (loads.contains(a)) {
				dependencies.add(new LoopDependencyException(a, currentIterationNumber, DependencyKind.ReadWrite));
				dependencyDetected = true;
			}
			
			currentIterationAccesses.add(a);
		}
		
		array[index] = value;
		return array;
	}
	
	private List<LoopDependencyException> listDependencies() {
		return dependencies;
	}
	
	private void _flushAfterIteration() {
		for (Access a: currentIterationAccesses) {
			switch (a.getKind()) {
				case Store:
					stores.add(a);
				break;
				
				case Load:
					loads.add(a);
				break;
			}
		}
		
		currentIterationAccesses.clear();
	}
	
	private void _flush() {
		loads = null;
		stores = null;
		
		try { _create_stores(); }
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void _create_stores() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		stores = _config.getLoopStoreClass().getDeclaredConstructor(TraceConfiguration.class).newInstance(_config.getLoopStoreConfiguration());
		loads = _config.getLoopStoreClass().getDeclaredConstructor(TraceConfiguration.class).newInstance(_config.getLoopStoreConfiguration());
		dependencies = new LinkedList<>();
	}
	
	private long getMemoryUsage() {
		return loads.memoryUsage() + stores.memoryUsage();
	}
}