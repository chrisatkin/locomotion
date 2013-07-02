package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.hotspot.meta.HotSpotResolvedJavaMethod;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.AccessIndexedNode;

abstract class ArrayBehaviourNode<T extends AccessIndexedNode> extends BehaviourNode<T> {
	private final HotSpotResolvedJavaMethod method;
	
	public ArrayBehaviourNode(T target) {
		super(target);
		this.method = (HotSpotResolvedJavaMethod) ((StructuredGraph) target.array().graph()).method();
	}
	
	public HotSpotResolvedJavaMethod getMethod() {
		return method;
	}
}
