package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.Stamp;
import com.oracle.graal.replacements.Snippets;

public abstract class ArrayAccessNode extends LocomotionNode implements Lowerable {
	public ArrayAccessNode(Stamp stamp) {
		super(stamp);
	}
}