package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;

public class ArrayStoreBehaviourNode<T extends Node> extends ArrayBehaviourNode<T> {
	private String t;
	
	public ArrayStoreBehaviourNode(String string) {
		super();
		this.t = string;
	}
	
	public String testString() {
		return t;
	}
}