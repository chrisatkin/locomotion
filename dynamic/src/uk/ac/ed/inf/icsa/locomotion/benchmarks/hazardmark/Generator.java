package uk.ac.ed.inf.icsa.locomotion.benchmarks.hazardmark;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;

public interface Generator {
	public Generator configure(final int length,
						  final int dependencies,
						  final long seed,
						  final double prob_writewrite,
						  final double prob_writeread,
						  final double prob_readwrite);
	
	public Generator generate();
	
	public int[] getA();
	
	public int[] getB();
	
	public AccessKind[] getAccessPattern();
}
