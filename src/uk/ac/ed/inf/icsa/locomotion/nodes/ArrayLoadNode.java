package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRGeneratorTool;
import com.oracle.graal.nodes.spi.LoweringTool;
import com.oracle.graal.nodes.type.Stamp;
import com.oracle.graal.nodes.type.StampFactory;

public class ArrayLoadNode extends ArrayAccessNode implements StoreOperation {

	public ArrayLoadNode() {
		super(StampFactory.forVoid());
		System.out.println("Arra LOAD");
	}
	
	public ArrayLoadNode(Stamp stamp) {
		super(stamp);
	}
	
	@Override
	public void generate(LIRGeneratorTool generator) {
		;
	}

}
