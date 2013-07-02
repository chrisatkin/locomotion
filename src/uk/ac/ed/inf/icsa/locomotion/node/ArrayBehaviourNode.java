package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.java.AccessIndexedNode;

abstract class ArrayBehaviourNode extends LocomotionNode {

	public ArrayBehaviourNode(AccessIndexedNode target) {
		super(target);
	}

}
