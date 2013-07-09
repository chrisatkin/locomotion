package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;
import com.oracle.graal.hotspot.meta.HotSpotResolvedJavaMethod;

abstract class ArrayBehaviourNode<T extends Node> extends BehaviourNode<T> {
	//private final HotSpotResolvedJavaMethod method;
	
	public ArrayBehaviourNode() {
		super();
		//this.method = (HotSpotResolvedJavaMethod) ((StructuredGraph) target.array().graph()).method();
	}
	
	public HotSpotResolvedJavaMethod getMethod() {
		//return method;
		return null;
	}
}
