package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public final class SomeDependent implements Experiment {
	private int length;
	private double probability;
	
	@Override
	public void setArguments(Object[] args) {
		this.length = (int) args[0];
		this.probability = (double) args[1];
	}

	@Override
	public void run(Output output, InstrumentSupport instrument) {
		Generator gen = new uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.SomeDependent(length, probability);
		gen.generate();
		
		Integer[] a = gen.getA();
		Integer[] b = gen.getB();
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer val = InstrumentSupport.arrayLookup(b, InstrumentSupport.arrayLookup(a, i, i, "some-dependent"), i, "some-dependent");
			
			InstrumentSupport.arrayWrite(c, i, val, i, "some-dependent");
		}
	}
	
	public String getIdentifier() {
		return "some-dependent length=" + length + " prob=" + probability;
	}

}
