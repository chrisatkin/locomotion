package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Replacements;
import com.oracle.graal.nodes.type.StampFactory;

abstract class LocomotionNode<T extends Node> extends FixedWithNextNode implements LIRLowerable {
	protected MetaAccessProvider runtime;
	protected Replacements replacements;
	protected TargetDescription target;
	
	protected LocomotionNode(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		super(StampFactory.forVoid());
		this.runtime = runtime;
		this.replacements = replacements;
		this.target = target;
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// Do nothing, node is structural only
	}
}