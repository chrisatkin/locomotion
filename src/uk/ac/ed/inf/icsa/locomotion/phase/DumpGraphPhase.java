package uk.ac.ed.inf.icsa.locomotion.phase;

import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.Phase;
import com.oracle.graal.printer.GraphPrinterDumpHandler;

public class DumpGraphPhase extends Phase {
	private String name;
	
	public DumpGraphPhase(String _name) {
		this.name = _name;
	}

	@Override
	protected void run(StructuredGraph graph) {
		GraphPrinterDumpHandler printer = new GraphPrinterDumpHandler();
		printer.dump(graph, this.name);
		printer.close();
		
		System.out.println("name=" + name + " method=" + graph.method().getName());
	}
}
