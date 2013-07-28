package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public class Test {
	private Experiment e;
	private InstrumentSupport instrument;
	
	public Test(Class <? extends Experiment> clazz, InstrumentSupport instrument, Object[] args) {
		this.instrument = instrument;
		
		try { this.e = clazz.newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
		
		e.setArguments(args);
	}
	
	public void run(Output output) {
		try {
			e.run(output, instrument);
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return e.getIdentifier();
	}
}