package uk.ac.ed.inf.icsa.locomotion;

import java.util.HashMap;

import com.oracle.graal.api.code.CompilationResult;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.Phase;
import com.oracle.graal.phases.PhasePlan;

import uk.ac.ed.inf.icsa.locomotion.misc.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.misc.Utils;
//import uk.ac.ed.inf.icsa.locomotion.phases.DumpGraphPhase;

public class Application {
	private Locomotion lm;
	
	public Application() {
		this.lm = new Locomotion();
	}
	
	@SuppressWarnings("serial")
	public void run() {
		for (String method: new String[] {"arrayAccess"})
			try {
				ResolvedJavaMethod rjm = Utils.getResolvedMethod(CodeSamples.class, method, this.lm.getRuntime());
				StructuredGraph graph = this.lm.parse(rjm);
				CompilationResult result = this.lm.compile(graph, rjm, new HashMap<Phase, PhasePlan.PhasePosition>() {{
					//put(new DumpGraphPhase("after-parsing"), PhasePosition.AFTER_PARSING);
					//put(new DumpGraphPhase("high-level"), PhasePosition.HIGH_LEVEL);
					//put(new DumpGraphPhase("low-level", PhasePosition.LOW_LEVEL));
					
					
				}});
				
				Utils.dumpGraphToIgv(graph, "arrayAccess");
				
				this.lm.execute(rjm, result, graph);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}
