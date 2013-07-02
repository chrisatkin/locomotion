package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.java.LoadIndexedNode;

public class ArrayLoadBehaviourNode extends ArrayBehaviourNode {
	public ArrayLoadBehaviourNode(LoadIndexedNode target) {
		super(target);
		System.out.println(this + " " + getMethod().getName());
	}	
}