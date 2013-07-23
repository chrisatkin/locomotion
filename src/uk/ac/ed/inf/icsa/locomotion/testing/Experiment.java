package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public interface Experiment {		
	public abstract void run(Output output, InstrumentSupport instrument);
	
	public abstract void setArguments(Object[] args);
	
	public abstract String getIdentifier();
}
