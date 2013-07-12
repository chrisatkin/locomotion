package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.spi.LoweringTool;

public final class LoadBehaviourNode extends BehaviourNode {
	public LoadBehaviourNode(LoadIndexedNode n, InstrumentationSnippets.Templates templates) {
		super(n, templates);
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
}