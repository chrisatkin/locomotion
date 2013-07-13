package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.ArrayInstrumentationSnippets;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.java.AccessIndexedNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.StampFactory;

abstract class BehaviourNode extends FixedWithNextNode implements Lowerable, LIRLowerable {
	public static class NodeInformation {
		private String name;
		
		private NodeInformation(AccessIndexedNode node) {
			this.name = node.toString(Verbosity.All);
		}
		
		public static NodeInformation getNodeInfo(AccessIndexedNode node) {
			return new NodeInformation(node);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	protected NodeInformation info;
	protected final ArrayInstrumentationSnippets.Templates templates;

	public BehaviourNode(AccessIndexedNode n, ArrayInstrumentationSnippets.Templates templates) {
		super(StampFactory.forVoid());
		this.info = NodeInformation.getNodeInfo(n);
		this.templates = templates;
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}

	public NodeInformation getNodeInfo() {
		return info;
	}

}