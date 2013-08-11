package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;
import static uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrumentation.*;
//import static uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport.arrayLookup;
//import static uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport.arrayWrite;
import static java.lang.Math.random;

public class HazardTest implements Experiment {
	
	private int[] a;
	private int[] b;
	private AccessKind[] pattern;
	private String ident;
	
	@Override
	public void run(Output output, InstrumentSupport instrument) throws Exception {		
//		for (int i = 0; i < a.length; i++) 
//			if (pattern[i] == AccessKind.Store)
//				arrayWrite(a, arrayLookup(b, i, i, getIdentifier()), (int) random() * a.length, i, getIdentifier());
//			else
//				arrayLookup(a, arrayLookup(b, i, i, getIdentifier()), i, getIdentifier());
		
		startLoop("blah");
		for (int i = 0; i < a.length; i++) {
			startIteration(i);
			
			if (pattern[i] == AccessKind.Store) {
				store(a, load(b, i), 1);
			} else {
				load(a, load(b, i));
			}
			
			endIteration();
		}
		endLoop();
	}

	@Override
	public void setArguments(Object[] args) {
		this.a = (int[]) args[0];
		this.b = (int[]) args[1];
		this.pattern = (AccessKind[]) args[2];
		this.ident = (String) args[3];
	}

	@Override
	public String getIdentifier() {
		return ident;
	}
	
	public int getLength() {
		return a.length;
	}

}
