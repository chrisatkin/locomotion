package uk.ac.ed.inf.icsa.locomotion.node;

import com.oracle.graal.hotspot.meta.HotSpotResolvedJavaMethod;
import com.oracle.graal.nodes.LoopBeginNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.AccessIndexedNode;

abstract class ArrayBehaviourNode extends LocomotionNode {

	private final HotSpotResolvedJavaMethod method;
	private final LoopBeginNode loop;
	
	public ArrayBehaviourNode(AccessIndexedNode target) {
		super(target);
		this.method = (HotSpotResolvedJavaMethod) ((StructuredGraph) target.array().graph()).method();
		this.loop = null;
	}
	
	public HotSpotResolvedJavaMethod getMethod() {
		return method;
	}
	
	public boolean isInLoop() {
		return loop != null;
	}
}