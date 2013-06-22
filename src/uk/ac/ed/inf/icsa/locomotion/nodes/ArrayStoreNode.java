package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.Stamp;
import com.oracle.graal.nodes.type.StampFactory;

public class ArrayStoreNode extends ArrayAccessNode implements StoreOperation {

	public ArrayStoreNode() {
		super(StampFactory.forVoid());
	}
	
	public ArrayStoreNode(Stamp stamp) {
		super(stamp);
	}
	
	@Override
	public void generate(LIRGeneratorTool generator) {
		;
	}
}
