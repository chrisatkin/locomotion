package uk.ac.ed.inf.icsa.locomotion.nodes;

import com.oracle.graal.nodes.spi.LIRGeneratorTool;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Entry;

public class ArrayStoreNode extends ArrayAccessNode implements StoreOperation {

	public ArrayStoreNode(Entry entry) {
		super(entry);
	}

	@Override
	public void generate(LIRGeneratorTool generator) {
		// TODO Auto-generated method stub
		
	}
}