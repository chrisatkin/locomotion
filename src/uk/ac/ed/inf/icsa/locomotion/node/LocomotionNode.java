package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.ValueNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.type.StampFactory;

abstract class LocomotionNode extends FixedWithNextNode implements LIRLowerable {

	protected ValueNode target;
	
	protected LocomotionNode(ValueNode target) {
		super(StampFactory.forVoid());
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
	
	public ValueNode getTarget() {
		return target;
	}
	
	public void inLoop() {
		
	}

}
