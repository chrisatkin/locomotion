package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;


public final class ExactVectorAddition implements Experiment {
	@Override
	public void run(Output output, Object[] args) {
		output.put("hello, world");
	}
}
