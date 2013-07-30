package uk.ac.ed.inf.icsa.locomotion.testing;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;

public interface Experiment {		
//	public abstract void run(Output output, InstrumentSupport instrument);
	
	public void run(Output output, InstrumentSupport instrument) throws Exception;
	
	public void setArguments(Object[] args);
	
	public String getIdentifier();
}
