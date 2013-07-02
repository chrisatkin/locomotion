package uk.ac.ed.inf.icsa.locomotion.phase;

import uk.ac.ed.inf.icsa.locomotion.node.ArrayLoadBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.node.ArrayStoreBehaviourNode;
import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.Replacements;

public class ArrayLoweringPhase extends LocomotionTemplatePhase {
	public ArrayLoweringPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(runtime, replacements, target);
	}

	@SuppressWarnings("unchecked")
	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof ArrayLoadBehaviourNode)
				new InstrumentationSnippets.Templates(runtime, replacements, target).lower((ArrayLoadBehaviourNode<LoadIndexedNode>) n);
			
			if (n instanceof ArrayStoreBehaviourNode)
				new InstrumentationSnippets.Templates(runtime, replacements, target).lower((ArrayStoreBehaviourNode<StoreIndexedNode>) n);
		}
	}
}