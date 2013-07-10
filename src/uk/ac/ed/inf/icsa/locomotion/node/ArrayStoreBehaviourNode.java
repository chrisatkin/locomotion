package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.nodes.spi.Lowerable.LoweringType;

public class ArrayStoreBehaviourNode<T extends WriteNode> extends ArrayBehaviourNode<T> {
		public ArrayStoreBehaviourNode(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(runtime, replacements, target);
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
	//	if (loweringType == LoweringType.AFTER_GUARDS)
			new InstrumentationSnippets.Templates(runtime, replacements, target).lower(this);
	}
}