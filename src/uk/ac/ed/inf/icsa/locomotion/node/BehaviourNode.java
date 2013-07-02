package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;
import com.oracle.graal.hotspot.meta.HotSpotResolvedJavaMethod;
import com.oracle.graal.nodes.LoopBeginNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.AccessIndexedNode;

abstract class BehaviourNode<T extends Node> extends LocomotionNode<T> {
	public BehaviourNode(T target) {
		super(target);
	}
}