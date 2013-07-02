package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.type.StampFactory;

abstract class LocomotionNode<T extends Node> extends FixedWithNextNode implements LIRLowerable {

	protected T target;
	
	protected LocomotionNode(T target) {
		super(StampFactory.forVoid());
		this.target = target;
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
	
	public T getTarget() {
		return target;
	}
}