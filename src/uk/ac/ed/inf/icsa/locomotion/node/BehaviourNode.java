package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.StampFactory;

abstract class BehaviourNode extends FixedWithNextNode implements Lowerable, LIRLowerable {

	public static class NodeInformation {
		private String name;
		
		private NodeInformation(StoreIndexedNode node) {
			this.name = node.toString(Verbosity.All);
		}
		
		private NodeInformation(LoadIndexedNode node) {
			this.name = node.toString(Verbosity.All);
		}
		
		public static NodeInformation getNodeInfo(StoreIndexedNode node) {
			return new NodeInformation(node);
		}
		
		public static NodeInformation getNodeInfo(LoadIndexedNode node) {
			return new NodeInformation(node);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	protected NodeInformation info;
	protected final InstrumentationSnippets.Templates templates;

	public BehaviourNode(LoadIndexedNode n, InstrumentationSnippets.Templates templates) {
		super(StampFactory.forVoid());
		this.info = NodeInformation.getNodeInfo(n);
		this.templates = templates;
	}
	
	public BehaviourNode(StoreIndexedNode n, InstrumentationSnippets.Templates templates) {
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