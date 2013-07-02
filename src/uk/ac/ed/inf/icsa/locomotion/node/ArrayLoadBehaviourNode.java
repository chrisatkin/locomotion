package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.StampFactory;

public class ArrayLoadBehaviourNode extends FixedWithNextNode implements Lowerable, LIRLowerable {
	private LoadIndexedNode target;
	
	public ArrayLoadBehaviourNode(LoadIndexedNode target) {
		super(StampFactory.forVoid());
		this.target = target;
	}

	@Override
	public void lower(LoweringTool tool, LoweringType loweringType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}

	public LoadIndexedNode getTarget() {
		return target;
	}
}