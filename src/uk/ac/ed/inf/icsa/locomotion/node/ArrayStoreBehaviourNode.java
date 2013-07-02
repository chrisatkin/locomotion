package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.java.StoreIndexedNode;

public class ArrayStoreBehaviourNode extends ArrayBehaviourNode {
	public ArrayStoreBehaviourNode(StoreIndexedNode target) {
		super(target);
	}
}