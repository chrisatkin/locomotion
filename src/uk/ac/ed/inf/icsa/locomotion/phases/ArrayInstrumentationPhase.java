package uk.ac.ed.inf.icsa.locomotion.phases;

import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayStoreNode;
import uk.ac.ed.inf.icsa.locomotion.nodes.ArrayLoadNode;
import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayAccessSnippets;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;

public class ArrayInstrumentationPhase extends LocomotionPhase {
	
	ArrayAccessSnippets.Templates templates;
	
	public ArrayInstrumentationPhase(ArrayAccessSnippets.Templates templates) {
		this.templates = templates;
	}
	
	@Override
	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof ArrayLoadNode)
				templates.lower((ArrayLoadNode) n);
			
			if (n instanceof ArrayStoreNode)
				templates.lower((ArrayStoreNode) n);
		}
	}

}
