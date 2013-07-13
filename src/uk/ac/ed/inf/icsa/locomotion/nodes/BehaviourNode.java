package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.type.StampFactory;

abstract class BehaviourNode extends LocomotionNode implements Lowerable, LIRLowerable {
	public BehaviourNode() {
		super(StampFactory.forVoid());
	}
	
	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
}