package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.Generator;
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
		Generator gen = new uk.ac.ed.inf.icsa.locomotion.benchmarks.probabilistic.StaticGenerator(this.length);
		gen.generate();
		
		Integer[] a = gen.getA();
		Integer[] b = gen.getB();
		
		Integer[] c = new Integer[a.length];
		
		for (int i = 0; i < a.length; i++) {
			Integer val = InstrumentSupport.arrayLookup(b, InstrumentSupport.arrayLookup(a, i, i, "all-dependent"), i, "all-dependent");
			
			InstrumentSupport.arrayWrite(c, i, val, i, "all-dependent");
		}
	}
	
	public String getIdentifier() {
		return "all-dependent;length=" + length;
	}

}
