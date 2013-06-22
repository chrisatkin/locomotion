package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.FixedNode;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.Stamp;

public abstract class LocomotionNode extends FixedWithNextNode {

	public LocomotionNode(Stamp stamp) {
		super(stamp);
		// TODO Auto-generated constructor stub
	}

}
