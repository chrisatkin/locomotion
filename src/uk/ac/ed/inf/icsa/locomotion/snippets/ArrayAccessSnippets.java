package uk.ac.ed.inf.icsa.locomotion.snippets;

import com.oracle.graal.api.code.CodeCacheProvider;
import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.JavaType;
import com.oracle.graal.nodes.InvokeNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.ValueNode;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.MethodCallTargetNode;
import com.oracle.graal.nodes.java.MethodCallTargetNode.InvokeKind;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.replacements.Snippet;
import com.oracle.graal.replacements.SnippetTemplate.AbstractTemplates;
import com.oracle.graal.replacements.SnippetTemplate.SnippetInfo;
import com.oracle.graal.replacements.Snippets;

public class ArrayAccessSnippets implements Snippets {
	
	@Snippet
	public static void arrayIndexStore() {
		
	}
	
	@Snippet
	public static void arrayIndexLoad() {	
		
	}
	
	public static class Templates extends AbstractTemplates {
				
		private final SnippetInfo methodOnLoad = snippet(ArrayAccessSnippets.class, "arrayIndexLoad");
		private final SnippetInfo methodOnStore = snippet(ArrayAccessSnippets.class, "arrayIndexStore");
		
		public Templates(CodeCacheProvider runtime, Replacements replacements, TargetDescription target) {
			super(runtime, replacements, target);
		}
		
		public void invokeAfter(StoreIndexedNode node, StructuredGraph graph) {
			JavaType returnType = methodOnStore.getMethod().getSignature().getReturnType(methodOnStore.getMethod().getDeclaringClass());
			MethodCallTargetNode callTarget = graph.add(new MethodCallTargetNode(InvokeKind.Static, methodOnStore.getMethod(), new ValueNode[] {}, returnType));
			InvokeNode invoke = graph.add(new InvokeNode(callTarget, 0));
			invoke.setStateAfter(node.stateAfter());
			graph.addAfterFixed(node, invoke);
		}
		
		public void instrument(LoadIndexedNode node, StructuredGraph graph) {
			JavaType returnType = methodOnLoad.getMethod().getSignature().getReturnType(methodOnLoad.getMethod().getDeclaringClass());
			MethodCallTargetNode callTarget = graph.add(new MethodCallTargetNode(InvokeKind.Static, methodOnLoad.getMethod(), new ValueNode[] {}, returnType));
			InvokeNode invoke = graph.add(new InvokeNode(callTarget, 0));
			invoke.setStateAfter(graph.start().stateAfter());
			graph.addAfterFixed(node, invoke);
		}
	}
}
