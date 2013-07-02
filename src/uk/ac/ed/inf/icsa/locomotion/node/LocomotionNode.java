package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.StartNode;
import com.oracle.graal.nodes.StructuredGraph;
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
	
	public String getMethodName() {
		return _getMethodName_Runner(this.target);
	}
	
	private String _getMethodName_Runner(Node node) {
		if (node instanceof StartNode)
			return null;
		
		if (node.graph() == null)
			return _getMethodName_Runner(node.predecessor());
		
		String name = ((StructuredGraph) node.graph()).method().getName();
		
		if (name != null)
			return name;
		else
			return _getMethodName_Runner(node.predecessor());
	}
}
