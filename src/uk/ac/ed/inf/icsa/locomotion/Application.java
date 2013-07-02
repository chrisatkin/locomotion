package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.misc.Utils;
import uk.ac.ed.inf.icsa.locomotion.phase.ArrayAccessInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.phase.ArrayAccessLoweringPhase;

import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

public class Application {
	Locomotion lm;
	
	public Application() {
		this.lm = new Locomotion(new Locomotion.Configuration() {{
			optimizations = OptimisticOptimizations.NONE;
		}});
	}
	
	@SuppressWarnings({"serial"})
	public void run() {
		for (String method: new String[] {"loopDepency"})
			try {
				ResolvedJavaMethod rjm = Utils.getResolvedMethod(CodeSamples.class, method, this.lm.getRuntime());
				StructuredGraph graph = this.lm.parse(rjm);
				CompilationResult result = this.lm.compile(graph, rjm, new HashMap<Phase, Locomotion.Position>() {{
					//put(new InstrumentWriteNodePhase(), Locomotion.Position.Low);	
					put (new ArrayAccessInstrumentationPhase(), Locomotion.Position.High);
					put(new ArrayAccessLoweringPhase(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget()), Locomotion.Position.High);
				}});
				
				this.lm.execute(rjm, result, graph);
				
				System.out.println("\n\nInstrumentation Report\n-------------------------\n" + Instrument.report());
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}
