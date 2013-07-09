package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;

abstract class BehaviourNode<T extends Node> extends LocomotionNode<T> {
	public BehaviourNode() {
		super();
	}
}