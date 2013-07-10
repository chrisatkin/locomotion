package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.extended.ReadNode;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.spi.Replacements;

public final class ArrayLoadBehaviourNode<T extends ReadNode> extends ArrayBehaviourNode<T> {
	public static class ArrayLoadNodeInformation extends ArrayNodeInformation {
		private String name;
		
		private ArrayLoadNodeInformation(ReadNode node) {
			this.name = node.toString();
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
	
	public ArrayLoadBehaviourNode(T n, InstrumentationSnippets.Templates templates) {
		super();
		this.info = ArrayLoadNodeInformation.getNodeInfo(n);
		this.templates = templates;
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		//	if (loweringType == LoweringType.AFTER_GUARDS)
			templates.lower(this);
	}
	
	public ArrayLoadNodeInformation getNodeInfo() {
		return info;
	}
}