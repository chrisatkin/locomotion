package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.StampFactory;

public final class ArrayStoreBehaviourNode extends FixedWithNextNode implements LIRLowerable, Lowerable {
	public static class ArrayStoreNodeInformation {
		private String name;
		
		private ArrayStoreNodeInformation(WriteNode node) {
			this.name = node.toString(Verbosity.All);
		}
		
		public static ArrayStoreNodeInformation getNodeInfo(WriteNode node) {
			return new ArrayStoreNodeInformation(node);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private ArrayStoreNodeInformation info;
	private final InstrumentationSnippets.Templates templates;
	
	public ArrayStoreBehaviourNode(WriteNode n, InstrumentationSnippets.Templates templates) {
		super(StampFactory.forVoid());
		this.info = ArrayStoreNodeInformation.getNodeInfo(n);
		this.templates = templates;
	}
	
	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		templates.lower(this);
	}
	
	public ArrayStoreNodeInformation getNodeInfo() {
		return info;
	}

	
}