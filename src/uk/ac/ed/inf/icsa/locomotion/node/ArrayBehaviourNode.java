package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.extended.AccessNode;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.Replacements;

abstract class ArrayBehaviourNode<T extends AccessNode> extends BehaviourNode<T> implements Lowerable {
	public static abstract class ArrayNodeInformation extends NodeInformation {
		public String toString() {
			return new StringBuilder().append("testString").toString();
		}
	}
	
	public ArrayBehaviourNode() {
		super();
	}
}