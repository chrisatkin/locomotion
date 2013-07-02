package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.java.LoadIndexedNode;

public class ArrayLoadBehaviourNode<T extends LoadIndexedNode> extends ArrayBehaviourNode<T> {
	public ArrayLoadBehaviourNode(T target) {
		super(target);
	}
	
	public int test() {
		return 9;
	}
}