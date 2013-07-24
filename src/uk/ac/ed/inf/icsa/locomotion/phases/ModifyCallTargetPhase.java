package uk.ac.ed.inf.icsa.locomotion.phases;

import uk.ac.ed.inf.icsa.locomotion.Application;
import uk.ac.ed.inf.icsa.locomotion.utilities.GraphUtilities;
import uk.ac.ed.inf.icsa.locomotion.utilities.MethodUtilities;

import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.meta.Kind;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.graph.Node;
import com.oracle.graal.hotspot.meta.HotSpotResolvedPrimitiveType;
import com.oracle.graal.nodes.Invoke;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.ValueNode;
import com.oracle.graal.nodes.java.MethodCallTargetNode;

public final class ModifyCallTargetPhase extends LocomotionPhase {
	
	private MetaAccessProvider runtime;
	
	public ModifyCallTargetPhase(MetaAccessProvider runtime) {
		this.runtime = runtime;
	}

	@Override
	protected void run(StructuredGraph graph) {
		GraphUtilities.dumpGraphToIgv(graph, "before-transform");
		
		for (Node node: graph.getNodes()) {
			if (node instanceof Invoke) {
				Invoke invokeNode = (Invoke) node;
				String targetName = invokeNode.callTarget().targetName();
				
				if (targetName.equals("callA")) {
					try {
						ResolvedJavaMethod method = MethodUtilities.getResolvedMethod((CodeCacheProvider) runtime, Application.class.getMethod("callB", new Class<?>[] {}));
						((MethodCallTargetNode) invokeNode.callTarget()).setTargetMethod(method);
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		GraphUtilities.dumpGraphToIgv(graph, "after-transform");
	}

}
