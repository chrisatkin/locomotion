package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.extended.WriteNode;

public class ArrayInstrumentationPhase extends LocomotionPhase {

	@Override
	protected void run(StructuredGraph graph) {
		for (Node n : graph.getNodes()) {
			if (n instanceof WriteNode)
				graph.addAfterFixed((WriteNode) n, graph.add(new ArrayStoreBehaviourNode<WriteNode>(n.toString())));

//			if (n instanceof StoreIndexedNode)
//				graph.addAfterFixed((StoreIndexedNode) n, graph
//						.add(new ArrayStoreBehaviourNode<StoreIndexedNode>((StoreIndexedNode) n)));
		}
	}

}
