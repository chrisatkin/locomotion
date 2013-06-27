package uk.ac.ed.inf.icsa.locomotion.phases;

import com.oracle.graal.graph.Node;
import com.oracle.graal.graph.Node.Verbosity;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.extended.WriteNode;

public class InstrumentWriteNodePhase extends LocomotionPhase {

	@Override
	protected void run(StructuredGraph graph) {
		for (Node n: graph.getNodes()) {
			if (n instanceof WriteNode) {
				WriteNode wn = (WriteNode) n;
				System.out.println(wn.location().toString(Verbosity.All));
			}
		}
	}

}
