package uk.ac.ed.inf.icsa.locomotion.nodes;

import uk.ac.ed.inf.icsa.locomotion.snippets.ArrayInstrumentationSnippets;

import com.oracle.graal.nodes.extended.AccessNode;

public abstract class ArrayBehaviourNode extends BehaviourNode {
	public static class NodeInformation {
		private String name;
		private String location;
		
		private NodeInformation(AccessNode node) {
			this.name = node.toString();
			this.location = node.location().toString(Verbosity.All);
		}
		
		public static NodeInformation getNodeInfo(AccessNode node) {
			return new NodeInformation(node);
		}
		
		@Override
		public String toString() {
			return name + " " + location;
		}
	}

	protected NodeInformation info;
	protected final ArrayInstrumentationSnippets.Templates templates;

	public ArrayBehaviourNode(AccessNode n, ArrayInstrumentationSnippets.Templates templates) {
		super();
		this.info = NodeInformation.getNodeInfo(n);
		this.templates = templates;
	}

	public NodeInformation getNodeInfo() {
		return info;
	}

}