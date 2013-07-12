package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;

import uk.ac.ed.inf.icsa.locomotion.core.Configuration;
import uk.ac.ed.inf.icsa.locomotion.core.Dispatch;
import uk.ac.ed.inf.icsa.locomotion.core.Method;
import uk.ac.ed.inf.icsa.locomotion.core.Position;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.phase.MemoryOperationInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

public class Application {
	private Dispatch lm;
	
	public Application() {
		this.lm = new Dispatch(new Configuration() {{
			optimizations = OptimisticOptimizations.NONE;
			level = Level.ALL;
		}});
	}
	
	@SuppressWarnings("serial")
	public void run() {
		for (Method m: new LinkedList<Method>() {{
			add(new Method() {{
				clazz = CodeSamples.class;
				name = "loopDependency";
				types = new Class<?>[] {int[].class, int[].class};
				arguments = new Object[]{ new Integer[] {} };
			}});
		}}) {
			try {
				lm.process(m, new HashMap<Phase, Position>() {{
					put(new MemoryOperationInstrumentationPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);	
				}});
				
                System.out.println("Loads: " + InstrumentationSnippets.loads);
                System.out.println("Stores: " + InstrumentationSnippets.stores);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}
