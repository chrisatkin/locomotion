package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;

public class ArrayAccessInstrumentationPhase extends LocomotionPhase {

	@Override
	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof LoadIndexedNode)
				graph.addAfterFixed((LoadIndexedNode) n, graph.add(new ArrayLoadBehaviourNode((LoadIndexedNode) n)));
				
			if (n instanceof StoreIndexedNode)
				graph.addAfterFixed((StoreIndexedNode) n, graph.add(new ArrayStoreBehaviourNode((StoreIndexedNode) n)));
		}
	}

}
