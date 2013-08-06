package uk.ac.ed.inf.icsa.locomotion.benchmarks.generated;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LocomotionError;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;

public abstract class Generator {
	protected int length;
	protected AccessKind[] first;
	protected AccessKind[] second;
	protected Integer[] array;
	
	protected Generator(int length) {
		this.length = length;
		this.first = null;
		this.second = null;
		this.array = null;
	}
	
	public abstract void generate();
	
	public AccessKind[] getFirst() {
		if (first == null)
			throw new LocomotionError("must call generate() first");
		
		return first;
	}
	
	public AccessKind[] getSecond() {
		if (second == null)
			throw new LocomotionError("must call generate() first");
		
		return second;
	}
	
	public Integer[] getArray() {
		if (array == null)
			throw new LocomotionError("must call generate() first");
		
		return array;
	}
}
