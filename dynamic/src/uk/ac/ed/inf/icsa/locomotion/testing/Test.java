package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public class Test {
	private Experiment e;
	private InstrumentSupport instrument;
	private Output output;
	private int length;
	
	public Test(Class <? extends Experiment> clazz, InstrumentSupport instrument, Object[] args, Output output, int length) {
		this.instrument = instrument;
		this.length = length;
		
		try { this.e = clazz.newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
		
		e.setArguments(args);
	}
	
	public int getLength() {
		return length;
	}
	
	public void run() {
		try {
//			long start = System.currentTimeMillis();
			e.run(output, instrument);
//			long stop = System.currentTimeMillis();
//			System.out.println((stop - start));
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