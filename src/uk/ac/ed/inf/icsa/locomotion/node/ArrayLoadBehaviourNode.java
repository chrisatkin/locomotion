package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;

public class ArrayLoadBehaviourNode<T extends Node> extends ArrayBehaviourNode<T> {
	private String t;
	
	public ArrayLoadBehaviourNode(String t) {
		super();
		this.t = t;
	}
	
	public String testString() {
		return t;
	}
}