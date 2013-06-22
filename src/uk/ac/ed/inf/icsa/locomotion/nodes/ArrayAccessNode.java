package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.Stamp;

public abstract class ArrayAccessNode extends LocomotionNode implements LIRLowerable {

	public ArrayAccessNode(Stamp stamp) {
		super(stamp);
		System.out.println("ArrayAccessNode");
	}
}