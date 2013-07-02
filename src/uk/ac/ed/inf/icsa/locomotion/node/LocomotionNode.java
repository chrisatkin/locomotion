package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.ValueNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.StampFactory;

abstract class LocomotionNode extends FixedWithNextNode implements Lowerable, LIRLowerable {

	protected ValueNode target;
	
	protected LocomotionNode(ValueNode target) {
		super(StampFactory.forVoid());
	}
	
	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
	
	public ValueNode getTarget() {
		return target;
	}

}
