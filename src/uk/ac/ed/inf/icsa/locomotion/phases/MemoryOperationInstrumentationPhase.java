package uk.ac.ed.inf.icsa.locomotion.phases;

import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;
import uk.ac.ed.inf.icsa.locomotion.utilities.GraphUtilities;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.phases.Phase;

public class MemoryOperationInstrumentationPhase extends Phase {
	private ArrayInstrumentationSnippets.Templates templates;
	
	public MemoryOperationInstrumentationPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		this.templates = new ArrayInstrumentationSnippets.Templates(runtime, replacements, target);
	}
	
	@Override
	protected void run(StructuredGraph graph) {
		for (Node node : graph.getNodes()) {
			if (node instanceof StoreIndexedNode && GraphUtilities.nodeInLoop(node))
				graph.addAfterFixed((StoreIndexedNode) node, graph.add(new ArrayStoreBehaviourNode((StoreIndexedNode) node, templates)));

			if (node instanceof LoadIndexedNode && GraphUtilities.nodeInLoop(node))
				graph.addAfterFixed((LoadIndexedNode) node, graph.add(new ArrayLoadBehaviourNode((LoadIndexedNode) node, templates)));
		}
		
//		GraphUtilities.dumpGraphToIgv(graph, "high-level");
	}

}