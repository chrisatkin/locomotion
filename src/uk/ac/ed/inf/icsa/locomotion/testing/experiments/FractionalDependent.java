package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.basic.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.generated.FractionalGenerator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.generated.Generator;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.generated.StaticGenerator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class FractionalDependent implements Experiment {
	private int length, num_ww, num_rw, num_wr;
	private double probability;
	
	@Override
	public void setArguments(Object[] args) {
		this.length = (int) args[0];
		this.num_ww = (int) args[1];
		this.num_wr = (int) args[2];
		this.num_rw = (int) args[3];
	}

	@Override
	public void run(Output output, InstrumentSupport instrument) {
		FractionalGenerator gen = new FractionalGenerator(length, num_ww, num_wr, num_rw);
		gen.generate();
		Kind[] first = gen.getFirst();
		Kind[] second = gen.getSecond();
		Integer[] array = gen.getArray();
		
		CodeSamples.loopDependency(array, first, second, getIdentifier());
	}
	
	public String getIdentifier() {
		return "fractional-dependent;length=" + length + ";prob=" + probability;
	}

}
