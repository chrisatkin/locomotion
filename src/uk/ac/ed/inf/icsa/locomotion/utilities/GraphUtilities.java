package uk.ac.ed.inf.icsa.locomotion.utilities;

import com.oracle.graal.graph.Graph;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.LoopBeginNode;
import com.oracle.graal.nodes.StartNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.printer.GraphPrinterDumpHandler;

public final class GraphUtilities {

	public static void dumpGraphToIgv(Graph graph, String name) {
		GraphPrinterDumpHandler printer = new GraphPrinterDumpHandler();
		printer.dump(graph, name);
		printer.close();
	}
	
	public static boolean nodeInLoop(Node node) {
		if (node instanceof StartNode)
			return false;
		
		if (node instanceof LoopBeginNode)
			return true;
		
		return nodeInLoop(node.predecessor());
	}

}
