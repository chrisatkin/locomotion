package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRLowerable;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;

public abstract class ArrayAccessNode extends LocomotionNode implements LIRLowerable {
	public ArrayAccessNode(Entry entry) {
		super(Kind.Array, entry);
	}
}