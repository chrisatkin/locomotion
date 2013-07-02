package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.java.StoreIndexedNode;

public class ArrayStoreBehaviourNode<T extends StoreIndexedNode> extends ArrayBehaviourNode<T> {
	public ArrayStoreBehaviourNode(T target) {
		super(target);
	}
}