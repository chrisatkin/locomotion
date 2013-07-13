package uk.ac.ed.inf.icsa.locomotion.nodes;

import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;

import com.oracle.graal.nodes.java.AccessIndexedNode;

abstract class ArrayBehaviourNode extends BehaviourNode {
	public static class NodeInformation {
		private String name;
		private AccessIndexedNode node;
		
		private NodeInformation(AccessIndexedNode node) {
			this.node = node;
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

	public ArrayBehaviourNode(AccessIndexedNode n, ArrayInstrumentationSnippets.Templates templates) {
		super();
		this.info = NodeInformation.getNodeInfo(n);
		this.templates = templates;
	}

	public NodeInformation getNodeInfo() {
		return info;
	}

}