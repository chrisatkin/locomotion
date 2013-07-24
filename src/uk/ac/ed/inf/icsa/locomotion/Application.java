package uk.ac.ed.inf.icsa.locomotion;

import static io.atkin.collections.literals.array;
import static io.atkin.collections.literals.list;
import static io.atkin.io.console.println;

import java.util.HashMap;
import java.util.logging.Level;

import uk.ac.ed.inf.icsa.locomotion.benchmarks.basic.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.core.Configuration;
import uk.ac.ed.inf.icsa.locomotion.core.Cycle;
import uk.ac.ed.inf.icsa.locomotion.core.Dispatch;
import uk.ac.ed.inf.icsa.locomotion.core.Position;
import uk.ac.ed.inf.icsa.locomotion.phases.DumpGraphPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.MemoryOperationInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.ModifyCallTargetPhase;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;

import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

public class Application {
	private Dispatch lm;
	
	public Application() {
		this.lm = new Dispatch(new Configuration() {{
			optimizations = OptimisticOptimizations.ALL;
			level = Level.ALL;
			debug = true;
		}});
	}
	
	@SuppressWarnings("serial")
	public void run() {
		for (Cycle cycle: list(
			new Cycle() {{
				clazz = Application.class;
				name = "experiment";
				types = array();
				arguments = array();
			}}
		)) {
			try {
				lm.process(cycle, new HashMap<Phase, Position>() {{
					//put(new MemoryOperationInstrumentationPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);
					//put(new DumpGraphPhase("high-level"), Position.High);
					//put(new ModifyCallTargetPhase(lm.getRuntime()), Position.High);
				}});
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
	
	public static void experiment() {
		callA();
	}
	
	public static void callA() {
		System.out.println("A");
	}
	
	public static void callB() {
		System.out.println("B");
	}
}
