package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippet.ArrayAccessSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.Replacements;

public class ArrayAccessLoweringPhase extends LocomotionTemplatePhase {
	public ArrayAccessLoweringPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(runtime, replacements, target);
	}

	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof ArrayLoadBehaviourNode)
				new ArrayAccessSnippets.Templates(runtime, replacements, target).lower((ArrayLoadBehaviourNode) n);
			
			if (n instanceof ArrayStoreBehaviourNode)
				new ArrayAccessSnippets.Templates(runtime, replacements, target).lower((ArrayStoreBehaviourNode) n);
		}
	}
}