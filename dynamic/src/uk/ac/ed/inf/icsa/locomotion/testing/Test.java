package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public class Test implements Runnable {
	private Experiment e;
	private InstrumentSupport instrument;
	private Output output;
	
	public Test(Class <? extends Experiment> clazz, InstrumentSupport instrument, Object[] args, Output output) {
		this.instrument = instrument;
		
		try { this.e = clazz.newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
		
		e.setArguments(args);
	}
	
	@Override
	public void run() {
		try {
			e.run(output, instrument);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return e.getIdentifier();
	}
	
	public Experiment getExperiment() {
		return e;
	}
}