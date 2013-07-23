package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class NoneDependent implements Experiment {
	private int length;

	@Override
	public void run(Output output, InstrumentSupport instrument) {
		Generator gen = new uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.NoneDependent(length);
		gen.generate();
		
		Integer[] a = gen.getA();
		Integer[] b = gen.getB();
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer val = InstrumentSupport.arrayLookup(b, InstrumentSupport.arrayLookup(a, i, i, "none-dependent"), i, "none-dependent");
			
			InstrumentSupport.arrayWrite(c, i, val, i, "none-dependent");
		}
	}

	@Override
	public void setArguments(Object[] args) {
		this.length = (int) args[0];
	}

	@Override
	public String getIdentifier() {
		return "none-dependent;length=" + length;
 	}

}
