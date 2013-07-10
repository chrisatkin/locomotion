package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.spi.Replacements;

public final class ArrayLoadBehaviourNode<T extends ReadNode> extends ArrayBehaviourNode<T> {	
	public ArrayLoadBehaviourNode(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(runtime, replacements, target);
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		//	if (loweringType == LoweringType.AFTER_GUARDS)
			new InstrumentationSnippets.Templates(runtime, replacements, target).lower(this);
	}
}