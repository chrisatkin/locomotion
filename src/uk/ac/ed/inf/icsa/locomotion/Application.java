package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;

import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.graph.Node;
import com.oracle.graal.graph.Node.Verbosity;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.phases.OptimisticOptimizations;
import com.oracle.graal.phases.Phase;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Instrument;
import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.misc.Utils;
import uk.ac.ed.inf.icsa.locomotion.phases.ArrayAccessNodeInsertationPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.ArrayInstrumentationPhase;
import uk.ac.ed.inf.icsa.locomotion.phases.LocomotionPhase;
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
				CompilationResult result = this.lm.compile(graph, rjm, new HashMap<Phase, Locomotion.Position>() {{
//					put(new ArrayAccessNodeInsertationPhase(), Locomotion.Position.High);
//					put(new ArrayInstrumentationPhase(new ArrayAccessSnippets.Templates(lm.getRuntime(), lm.getReplacements(), lm.getRuntime().getTarget())), Locomotion.Position.High);
					
					put(new LocomotionPhase() {
						protected void run(StructuredGraph graph) {
							for (Node n: graph.getNodes())
								if (n instanceof WriteNode) {
									WriteNode wn = (WriteNode) n;
									System.out.println(wn.toString(Verbosity.All));
								}
						}}, Locomotion.Position.Low);
				}});
				
				//Utils.dumpGraphToIgv(graph, "arrayAccess");
				
				this.lm.execute(rjm, result, graph);
				
				System.out.println(Instrument.report());
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}
