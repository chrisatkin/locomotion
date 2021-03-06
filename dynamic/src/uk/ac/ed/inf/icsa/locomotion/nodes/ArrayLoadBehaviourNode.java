package uk.ac.ed.inf.icsa.locomotion.nodes;

import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;

import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.spi.LoweringTool;

public final class ArrayLoadBehaviourNode extends ArrayBehaviourNode {
	public ArrayLoadBehaviourNode(ReadNode n, ArrayInstrumentationSnippets.Templates templates) {
		super(n, templates);
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
}