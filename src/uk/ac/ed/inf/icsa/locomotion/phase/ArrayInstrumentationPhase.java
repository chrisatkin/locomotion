package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.Application;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.extended.WriteNode;

public class ArrayInstrumentationPhase extends LocomotionPhase {

	@Override
	protected void run(StructuredGraph graph) {
		for (Node n : graph.getNodes()) {
			if (n instanceof WriteNode)
				graph.addAfterFixed((WriteNode) n, graph.add(new ArrayStoreBehaviourNode<WriteNode>(Application.runtime, Application.replacements, Application.target)));

			if (n instanceof ReadNode)
				graph.addAfterFixed((ReadNode) n, graph.add(new ArrayLoadBehaviourNode<ReadNode>(Application.runtime, Application.replacements, Application.target)));
		}
	}

}
