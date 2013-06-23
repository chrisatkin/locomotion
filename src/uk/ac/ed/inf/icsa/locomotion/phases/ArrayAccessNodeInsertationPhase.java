package uk.ac.ed.inf.icsa.locomotion.phases;

import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayAccessNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreNode;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.AccessIndexedNode;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;

public class ArrayAccessNodeInsertationPhase extends LocomotionPhase {

	@Override
	protected void run(StructuredGraph graph) {
		for (Node node: graph.getNodes()) {
			if (node instanceof StoreIndexedNode)
				markStore((StoreIndexedNode) node, graph);
			
			if (node instanceof LoadIndexedNode)
				markLoad((LoadIndexedNode) node, graph);
		}
	}
	
	private void markStore(StoreIndexedNode node, StructuredGraph graph) {
		_insertNode(graph.add(new ArrayStoreNode()), node, graph, InsertationPosition.After);
	}
	
	private void markLoad(LoadIndexedNode node, StructuredGraph graph) {
		_insertNode(graph.add(new ArrayLoadNode()), node, graph, InsertationPosition.After);
	}
	
	private void _insertNode(ArrayAccessNode insert, AccessIndexedNode node, StructuredGraph graph, InsertationPosition position) {
		switch (position) {
			case Before:
				graph.addBeforeFixed(node, insert);
			break;
			
			case After:
				graph.addAfterFixed(node, insert);
			break;
		}
	}
}
