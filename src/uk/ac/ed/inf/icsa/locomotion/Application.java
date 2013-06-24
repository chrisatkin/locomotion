package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;

import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;
import com.oracle.graal.phases.PhasePlan;
import com.oracle.graal.phases.PhasePlan.PhasePosition;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.ArrayAccessInstrumentation;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.misc.Utils;
import uk.ac.ed.inf.icsa.locomotion.phases.ArrayAccessNodeInsertationPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.ArrayInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.DumpGraphPhase;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayAccessSnippets;

public class Application {
	private Locomotion lm;
	
	public Application() {
		this.lm = new Locomotion(new Locomotion.Configuration() {{
			optimizations = OptimisticOptimizations.NONE;
		}});
	}
	
	@SuppressWarnings("serial")
	public void run() {
		for (String method: new String[] {"vectorAddition"})
			try {
				ResolvedJavaMethod rjm = Utils.getResolvedMethod(CodeSamples.class, method, this.lm.getRuntime());
				StructuredGraph graph = this.lm.parse(rjm);
				CompilationResult result = this.lm.compile(graph, rjm, new HashMap<Phase, PhasePlan.PhasePosition>() {{
					put(new ArrayAccessNodeInsertationPhase(), PhasePosition.HIGH_LEVEL);
					put(new ArrayInstrumentationPhase(new ArrayAccessSnippets.Templates(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget())), PhasePosition.HIGH_LEVEL);
				}});
				
				//Utils.dumpGraphToIgv(graph, "arrayAccess");
				
				this.lm.execute(rjm, result, graph);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		
		System.out.println("Stores: " + ArrayAccessInstrumentation.getStoreCount());
		System.out.println("Loads: " + ArrayAccessInstrumentation.getLoadCount());
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}
