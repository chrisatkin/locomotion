package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;

public final class StoreBehaviourNode extends BehaviourNode implements LIRLowerable, Lowerable {
	public StoreBehaviourNode(WriteNode n, InstrumentationSnippets.Templates templates) {
		super(n, templates);
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
}