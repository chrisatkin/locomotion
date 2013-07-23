package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;


public final class VectorAddition implements Experiment {
	private int length;
	private Integer[] a;
	private Integer[] b;
	
	@Override
	public void setArguments(Object[] args) {
		this.a = (Integer[]) args[0];
		this.b = (Integer[]) args[1];
		
		assert this.a.length == this.b.length: "vectors must be same length";
		
		this.length = a.length;
	}
	
	@Override
	public void run(Output output, InstrumentSupport instrument) {
		Integer[] c = new Integer[length];
		
		for (int i = 0; i < length; i++) {
			Integer currentA = InstrumentSupport.arrayLookup(a, i, i, "vector-addition");
			Integer currentB = InstrumentSupport.arrayLookup(b, i, i, "vector-addition");
			Integer result = currentA + currentB;
			
			InstrumentSupport.arrayWrite(c, i, result, i, "vector-addition");
		}
	}
	
	@Override
	public String getIdentifier() {
		return "vector-addition;length=" + length;
	}
}