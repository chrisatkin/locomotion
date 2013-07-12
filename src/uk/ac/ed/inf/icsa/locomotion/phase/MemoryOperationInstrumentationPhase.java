package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.LoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.StoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.phases.Phase;

public class MemoryOperationInstrumentationPhase extends Phase {
	private InstrumentationSnippets.Templates templates;
	
	public MemoryOperationInstrumentationPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		this.templates = new InstrumentationSnippets.Templates(runtime, replacements, target);
	}
	
	@Override
	protected void run(StructuredGraph graph) {
		for (Node n : graph.getNodes()) {
			if (n instanceof StoreIndexedNode)
				graph.addAfterFixed((StoreIndexedNode) n, graph.add(new StoreBehaviourNode((StoreIndexedNode) n, templates)));

			if (n instanceof LoadIndexedNode)
				graph.addAfterFixed((LoadIndexedNode) n, graph.add(new LoadBehaviourNode((LoadIndexedNode) n, templates)));
		}
	}

}