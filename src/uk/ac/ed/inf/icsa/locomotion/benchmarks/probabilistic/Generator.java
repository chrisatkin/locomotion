package uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic;

import uk.ac.ed.inf.icsa.locomotion.exceptions.LocomotionError;

public abstract class Generator {
	private Integer[] a;
	private Integer[] b;
	protected final int length;
	
	public Generator(int length) {
		this.a = null;
		this.b = null;
		this.length = length;
	}
	
	public abstract void generate();
	
	protected void setA(Integer[] a) {
		this.a = a;
	}
	
	public Integer[] getA() {
		if (a == null)
			throw new LocomotionError("a has not yet been set, call generate() first");
		
		return a;
	}
	
	protected void setB(Integer[] b) {
		this.b = b;
	}
	
	public Integer[] getB() {
		if (b == null)
			throw new LocomotionError("b has not yet been set, call generate() first");
		
		return b;
	}
}
