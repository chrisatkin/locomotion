package uk.ac.ed.inf.icsa.locomotion.testing.experiments;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.AccessKind;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.InstrumentSupport;
import uk.ac.ed.inf.icsa.locomotion.testing.Experiment;
import uk.ac.ed.inf.icsa.locomotion.testing.output.Output;
import static uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrumentation.*;
import static java.lang.Math.random;

public class HazardTestComputation implements Experiment {
	
	private int[] a;
	private int[] b;
	private AccessKind[] pattern;
	private String ident;
	private int numberOfComputations;
	private float d, e, f;
	
	public HazardTestComputation() {
		this.d = (float) (random() * 10000);
		this.e = this.f = 0;
	}
	
	@Override
	public void run(Output output, InstrumentSupport instrument) throws Exception {		

		startLoop("blah");
		for (int i = 0; i < a.length; i++) {
			startIteration(i);
			
			if (pattern[i] == AccessKind.Store) {
				store(a, load(b, i), 1);
			} else {
				load(a, load(b, i));
			}
			
			for (int j = 0; j < numberOfComputations; j++) {
				// perform two floating-point operations, one for each store/load
				f = d * d;
				e = f * d;
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
		this.numberOfComputations = (int) args[4];
	}

	@Override
	public String getIdentifier() {
		return ident;
	}
	
	public int getLength() {
		return a.length;
	}
	
	public float getF() {
		return f;
	}
	
	public float getE() {
		return e;
	}

}
