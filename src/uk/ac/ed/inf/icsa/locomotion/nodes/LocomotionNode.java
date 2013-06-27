package uk.ac.ed.inf.icsa.locomotion.nodes;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;
import uk.ac.ed.inf.icsa.locomotion.instrumentation.Kind;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.type.StampFactory;

public abstract class LocomotionNode extends FixedWithNextNode {
	
	protected Kind kind;
	protected Entry entry;
	

	public LocomotionNode(Kind kind, Entry entry) {
		super(StampFactory.forVoid());
		this.kind = kind;
		this.entry = entry;
	}
	
	public Kind getKind() {
		return kind;
	}
	
	public Entry getEntry() {
		return entry;
	}
}
