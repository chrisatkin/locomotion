package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.nbody.*;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class NBody implements Experiment {
	private String file;
	private int steps;

	@Override
	public void run(Output output, InstrumentSupport instrument) throws Exception {
		Universe.main(new String[] {file, Integer.toString(steps)});
	}

	@Override
	public void setArguments(Object[] args) {
		this.file = (String) args[0];
		this.steps = (int) args[1];
	}

	@Override
	public String getIdentifier() {
		return ("universe;config=" + file + ";length=" + steps).replaceAll("/", "-");
	}
}