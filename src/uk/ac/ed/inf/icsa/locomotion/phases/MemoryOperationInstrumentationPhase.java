package uk.ac.ed.inf.icsa.locomotion.phases;

import static uk.ac.ed.inf.icsa.locomotion.utilities.DebugUtilities.noop;
import uk.ac.ed.inf.icsa.locomotion.benchmarks.basic.CodeSamples;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;
import uk.ac.ed.inf.icsa.locomotion.utilities.GraphUtilities;
import uk.ac.ed.inf.icsa.locomotion.utilities.MethodUtilities;

import com.oracle.graal.api.meta.ResolvedJavaMethod;
import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.Constant;
import com.oracle.graal.api.meta.Kind;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.hotspot.meta.HotSpotResolvedPrimitiveType;
import com.oracle.graal.nodes.ConstantNode;
import com.oracle.graal.nodes.FrameState;
import com.oracle.graal.nodes.InvokeNode;
import com.oracle.graal.nodes.MergeNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.ValueNode;
import com.oracle.graal.nodes.calc.IntegerAddNode;
import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreFieldNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.nodes.java.*;

public class MemoryOperationInstrumentationPhase extends LocomotionPhase {
	//private ArrayInstrumentationSnippets.Templates templates;
	private MetaAccessProvider runtime;
	
	public MemoryOperationInstrumentationPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		this.runtime = runtime;
		//this.templates = new ArrayInstrumentationSnippets.Templates(runtime, replacements, target);
	}
	
	@Override
	protected void run(StructuredGraph graph) {
		graph.getNodes(LoadIndexedNode.class);
		for (Node node: graph.getNodes()) {	
//			if (node instanceof StoreIndexedNode/* || node instanceof LoadIndexedNode*/ && GraphUtilities.nodeInLoop(node))
//				noop();
//			
			if (node instanceof LoadIndexedNode)
				noop();
//			
//			if (node instanceof WriteNode/* && GraphUtilities.nodeInLoop(node)*/)
//				graph.addAfterFixed((WriteNode) node, graph.add(new ArrayStoreBehaviourNode((WriteNode) node, templates)));
//
//			if (node instanceof ReadNode/* && GraphUtilities.nodeInLoop(node)*/)
//				graph.addAfterFixed((ReadNode) node, graph.add(new ArrayLoadBehaviourNode((ReadNode) node, templates)));
			
//			if (node instanceof LoadIndexedNode) {
//				LoadIndexedNode lin = ((LoadIndexedNode) node);
//				lin.index().
			//}
			
//			if (node instanceof LoadIndexedNode) {
//				LoadIndexedNode lin = (LoadIndexedNode) node;
//				ValueNode index = (ValueNode) lin.index();
//				
//				if (index instanceof ConstantNode) {
//				
//				//index.
//				
//				//ConstantNode y = graph.add(new ConstantNode(Constant.INT_1));
//				
//				ConstantNode y = ConstantNode.forInt(1, graph);
//				IntegerAddNode add = graph.add(new IntegerAddNode(Kind.Int, (ConstantNode) index, y));
//				
//				LoadIndexedNode new_lin = graph.add(new LoadIndexedNode(lin.array(), add, lin.kind()));
//				
//				graph.replaceFixedWithFixed(lin, new_lin);
//				}
			
//			if (node instanceof LoadIndexedNode) {
//				LoadIndexedNode lin = (LoadIndexedNode) node;
//				
//				try {
//					ResolvedJavaMethod method = MethodUtilities.getResolvedMethod((CodeCacheProvider) runtime, CodeSamples.class.getMethod("testInstrument", new Class<?>[] {}));
//					MethodCallTargetNode call = graph.add(new MethodCallTargetNode(MethodCallTargetNode.InvokeKind.Static, method, new ValueNode[] {}, new HotSpotResolvedPrimitiveType(Kind.Void)));
//					InvokeNode invoke = graph.add(new InvokeNode(call, 0));
//					invoke.setUseForInlining(true);
////					invoke.setStateAfter(graph.add(new FrameState(FrameState.UNKNOWN_BCI)));
//					graph.addAfterFixed(lin, invoke);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			//}
		}
		
//		GraphUtilities.dumpGraphToIgv(graph, "high-level");
	}

}