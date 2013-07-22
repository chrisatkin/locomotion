package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public interface Experiment {		
	public abstract void run(Output output, Object[] args);
}
