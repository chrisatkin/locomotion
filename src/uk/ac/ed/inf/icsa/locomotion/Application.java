package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;

import uk.ac.ed.inf.icsa.locomotion.core.Configuration;
import uk.ac.ed.inf.icsa.locomotion.core.Dispatch;
import uk.ac.ed.inf.icsa.locomotion.core.Cycle;
import uk.ac.ed.inf.icsa.locomotion.core.Position;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.phase.DumpGraphPhase;
import uk.ac.ed.inf.icsa.locomotion.phase.MemoryOperationInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.snippet.ArrayInstrumentationSnippets;

import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

public class Application {
	private Dispatch lm;
	
	public Application() {
		this.lm = new Dispatch(new Configuration() {{
			optimizations = OptimisticOptimizations.NONE;
			level = Level.ALL;
			debug = true;
		}});
	}
	
	@SuppressWarnings("serial")
	public void run() {
		for (Cycle m: new LinkedList<Cycle>() {{
			add(new Cycle() {{
				clazz = CodeSamples.class;
				name = "loopDependency";
				types = new Class<?>[] {int[].class, int[].class};
				arguments = new Object[]{ new Integer[] {} };
			}});
		}}) {
			try {
				lm.process(m, new HashMap<Phase, Position>() {{
					put(new MemoryOperationInstrumentationPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);	
					put (new DumpGraphPhase("test"), Position.Mid);
				}});
				
                System.out.println("Loads: " + ArrayInstrumentationSnippets.loads);
                System.out.println("Stores: " + ArrayInstrumentationSnippets.stores);
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
