package uk.ac.ed.inf.icsa.locomotion.phases;

import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.FixedWithNextNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.java.LoadIndexedNode;
import com.oracle.graal.nodes.java.StoreIndexedNode;
import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LIRLowerable;
import com.oracle.graal.nodes.spi.Lowerable;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.StampFactory;

public class ArrayAccessInstrumentationPhase extends LocomotionPhase {

	public class ArrayStoreBehaviourNode extends FixedWithNextNode implements Lowerable, LIRLowerable {
		private StoreIndexedNode target;
		
		public ArrayStoreBehaviourNode(StoreIndexedNode target) {
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
		
	}
	
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
	}
	
	

	@Override
	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof LoadIndexedNode)
				graph.addAfterFixed((LoadIndexedNode) n, graph.add(new ArrayLoadBehaviourNode((LoadIndexedNode) n)));
				
			if (n instanceof StoreIndexedNode)
				graph.addAfterFixed((StoreIndexedNode) n, graph.add(new ArrayStoreBehaviourNode((StoreIndexedNode) n)));
		}
	}

}
