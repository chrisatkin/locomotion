package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.spi.Replacements;

abstract class BehaviourNode<T extends FixedWithNextNode> extends LocomotionNode<T> {
	public BehaviourNode() {
		super();
	}
}