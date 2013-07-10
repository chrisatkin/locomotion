package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.StampFactory;

public final class ArrayLoadBehaviourNode extends FixedWithNextNode implements LIRLowerable, Lowerable {
	public static class ArrayLoadNodeInformation {
		private String name;
		
		private ArrayLoadNodeInformation(ReadNode node) {
			this.name = node.toString(Verbosity.All);
		}
		
		public static ArrayLoadNodeInformation getNodeInfo(ReadNode node) {
			return new ArrayLoadNodeInformation(node);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private ArrayLoadNodeInformation info;
	private InstrumentationSnippets.Templates templates;
	
	public ArrayLoadBehaviourNode(ReadNode n, InstrumentationSnippets.Templates templates) {
		super(StampFactory.forVoid());
		this.info = ArrayLoadNodeInformation.getNodeInfo(n);
		this.templates = templates;
	}
	
	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
	
	public ArrayLoadNodeInformation getNodeInfo() {
		return info;
	}
}