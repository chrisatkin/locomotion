package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.ArrayInstrumentationSnippets;

import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.spi.LoweringTool;

public final class LoadBehaviourNode extends BehaviourNode {
	public LoadBehaviourNode(LoadIndexedNode n, ArrayInstrumentationSnippets.Templates templates) {
		super(n, templates);
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
}