package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.type.Stamp;

abstract class LocomotionNode extends FixedWithNextNode {
	public LocomotionNode(Stamp stamp) {
		super(stamp);
	}
}