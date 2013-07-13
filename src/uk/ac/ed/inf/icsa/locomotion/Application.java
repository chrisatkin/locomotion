package uk.ac.ed.inf.icsa.locomotion;

import static io.atkin.collections.literals.array;
import static io.atkin.collections.literals.list;
import static io.atkin.io.console.println;

import java.util.HashMap;
import java.util.logging.Level;

import uk.ac.ed.inf.icsa.locomotion.core.Configuration;
import uk.ac.ed.inf.icsa.locomotion.core.Cycle;
import uk.ac.ed.inf.icsa.locomotion.core.Dispatch;
import uk.ac.ed.inf.icsa.locomotion.core.Position;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.phases.MemoryOperationInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;

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
		for (Cycle cycle: list(
			new Cycle() {{
				clazz = CodeSamples.class;
				name = "loopDependency";
				types = array(Integer[].class, Integer[].class);
				arguments = array(array(0, 3, 4, 1, 2), array(3, 4, 0, 2, 1));
			}}
			
//			,new Cycle() {{
//				clazz = CodeSamples.class;
//				name = "vectorAddition";
//				types = array(Integer[].class, Integer[].class);
//				arguments = array(array(0, 3, 4, 1, 2), array(3, 4, 0, 2, 1));
//			}}
		)) {
			try {
				lm.process(cycle, new HashMap<Phase, Position>() {{
					put(new MemoryOperationInstrumentationPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);
				}});
				
                println("Loads: " + ArrayInstrumentationSnippets.loads);
                println("Stores: " + ArrayInstrumentationSnippets.stores);
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
