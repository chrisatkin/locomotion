package uk.ac.ed.inf.icsa.locomotion.phase;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.Replacements;

public class ArrayLoweringPhase extends LocomotionTemplatePhase {
	public ArrayLoweringPhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(runtime, replacements, target);
	}

	protected void run(StructuredGraph graph) {
//		for (Node n: graph.getNodes()) {
//			if (n instanceof ArrayLoadBehaviourNode)
//				new InstrumentationSnippets.Templates(runtime, replacements, target).lower((ArrayLoadBehaviourNode<ReadNode>) n);
//			
//			if (n instanceof ArrayStoreBehaviourNode)
//				new InstrumentationSnippets.Templates(runtime, replacements, target).lower((ArrayStoreBehaviourNode<WriteNode>) n);
//		}
	}
}