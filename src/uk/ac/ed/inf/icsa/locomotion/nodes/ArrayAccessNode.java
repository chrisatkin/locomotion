package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.Stamp;

public class ArrayAccessNode extends LocomotionNode {

	public ArrayAccessNode(Stamp stamp) {
		super(stamp);
		System.out.println("");
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		// TODO Auto-generated method stub		
	}
}