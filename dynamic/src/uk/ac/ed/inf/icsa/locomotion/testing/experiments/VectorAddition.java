package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;


public final class VectorAddition implements Experiment {
	private int length;
	
	@Override
	public void setArguments(Object[] args) {
		this.length = (int) args[0];
	}
	
	@Override
	public void run(Output output, InstrumentSupport instrument) {
		// vector addition
		int[] a = new int[length];
		int[] b = new int[length];
		
		for (int i = 0; i < length; i++) {
			a[i] = (int) Math.random() * i;
			b[i] = (int) Math.random() * i;
		}
		
		int[] c = new int[length];
		
		for (int i = 0; i < length; i++) {
			int currentA = InstrumentSupport.arrayLookup(a, i, i, getIdentifier());
			int currentB = InstrumentSupport.arrayLookup(b, i, i, getIdentifier());
			int result = currentA + currentB;
			
			InstrumentSupport.arrayWrite(c, i, result, i, getIdentifier());
		}
	}
	
	@Override
	public String getIdentifier() {
		return "vector-addition;length=" + length;
	}
}