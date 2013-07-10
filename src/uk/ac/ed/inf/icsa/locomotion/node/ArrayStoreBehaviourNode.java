package uk.ac.ed.inf.icsa.locomotion.node;

import uk.ac.ed.inf.icsa.locomotion.snippet.InstrumentationSnippets;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.extended.WriteNode;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.spi.Replacements;

public final class ArrayStoreBehaviourNode<T extends WriteNode> extends ArrayBehaviourNode<T> {
	public static class ArrayStoreNodeInformation extends ArrayNodeInformation {
		private ArrayStoreNodeInformation(WriteNode node) {
			
		}
		
		public static ArrayStoreNodeInformation getNodeInfo(WriteNode node) {
			return new ArrayStoreNodeInformation(node);
		}
		
		@Override
		public String toString() {
			return "store info";
		}
	}
	
	private ArrayStoreNodeInformation info;
	private final InstrumentationSnippets.Templates templates;
	
	public ArrayStoreBehaviourNode(T n, InstrumentationSnippets.Templates templates) {
		super();
		this.info = ArrayStoreNodeInformation.getNodeInfo(n);
		this.templates = templates;
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		//if (loweringType == LoweringType.AFTER_GUARDS)
			templates.lower(this);
	}
	
	public ArrayStoreNodeInformation getNodeInfo() {
		return info;
	}
}