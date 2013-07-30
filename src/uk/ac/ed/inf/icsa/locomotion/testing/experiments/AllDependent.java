package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.basic.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.generated.StaticGenerator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class AllDependent implements Experiment {
	
	private int length;
	
	@Override
	public void setArguments(Object[] args) {
		this.length = (int) args[0];
	}

	@Override
	public void run(Output output, InstrumentSupport instrument) {
		StaticGenerator gen = new StaticGenerator(length, Kind.Store, Kind.Store);
		gen.generate();
		Kind[] first = gen.getFirst();
		Kind[] second = gen.getSecond();
		Integer[] array = gen.getArray();
		
		CodeSamples.loopDependency(array, first, second, getIdentifier());
	}
	
	public String getIdentifier() {
		return "all-dependent;length=" + length;
	}

}
