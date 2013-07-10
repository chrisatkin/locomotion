package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.LoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.StoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.extended.WriteNode;
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
			if (n instanceof WriteNode)
				graph.addAfterFixed((WriteNode) n, graph.add(new StoreBehaviourNode((WriteNode) n, templates)));

			if (n instanceof ReadNode)
				graph.addAfterFixed((ReadNode) n, graph.add(new LoadBehaviourNode((ReadNode) n, templates)));
		}
	}

}
