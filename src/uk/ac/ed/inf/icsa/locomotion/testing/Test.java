package uk.ac.ed.inf.icsa.locomotion.testing;

import java.lang.reflect.InvocationTargetException;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public class Test {
	private Class<? extends Experiment> clazz;
	private Experiment e;
	private InstrumentSupport instrument;
	private Object[] args;
	
	public Test(Class <? extends Experiment> clazz, InstrumentSupport instrument, Object[] args) {
		this.clazz = clazz;
		this.instrument = instrument;
		this.args = args;
		
		try { this.e = clazz.newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
		
		e.setArguments(args);
	}
	
	public void run(Output output) {
		try {
			clazz.newInstance().run(output, instrument);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return e.getIdentifier();
	}
}