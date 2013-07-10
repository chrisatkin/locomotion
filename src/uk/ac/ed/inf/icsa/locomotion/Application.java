package uk.ac.ed.inf.icsa.locomotion;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;

import uk.ac.ed.inf.icsa.locomotion.core.Configuration;
import uk.ac.ed.inf.icsa.locomotion.core.Dispatch;
import uk.ac.ed.inf.icsa.locomotion.core.Method;
import uk.ac.ed.inf.icsa.locomotion.core.Position;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.misc.Utils;
import uk.ac.ed.inf.icsa.locomotion.phase.ArrayInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.phase.ArrayLoweringPhase;
import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

public class Application {
	public static MetaAccessProvider runtime;
	public static Replacements replacements;
	public static TargetDescription target;
	
	private Dispatch lm;
	
	public Application() {
		this.lm = new Dispatch(new Configuration() {{
			optimizations = OptimisticOptimizations.NONE;
		}});
	}
	
	@SuppressWarnings({"serial"})
	public void run() {
		runtime = lm.getRuntime();
		replacements = lm.getReplacements();
		target = lm.getRuntime().getTarget();
		
		for (Method<?> m: new LinkedList<Method<?>>() {{
			add(new Method<Integer>() {{
				clazz = CodeSamples.class;
				name = "loopDependency";
				types = new Class<?>[] {int[].class, int[].class};
				arguments = new Object[]{ new Integer[] {} };
			}});
		}}) {
			try {
				ResolvedJavaMethod rjm = Utils.getResolvedMethod(m, this.lm.getRuntime());
				StructuredGraph graph = lm.parse(rjm);
				CompilationResult result = lm.compile(graph, rjm, new HashMap<Phase, Position>() {{
					put(new ArrayInstrumentationPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);	
					//put(new DumpGraphPhase("high-level"), Position.High);
					//put(new ArrayLoweringPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Position.High);
				}});
				
				lm.execute(rjm, result, graph);
				
				System.out.println("Loads: " + InstrumentationSnippets.loads);
				System.out.println("Stores: " + InstrumentationSnippets.stores);
				System.out.println("\n\nInstrumentation Report\n-------------------------\n" + Instrument.report());
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
