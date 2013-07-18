package uk.ac.ed.inf.icsa.locomotion.instrumentation.graph;

import java.util.List;
import java.util.ArrayList;

public class DependencyGraph {
	private final int loopId;
	private final List<Node> nodes;
	
	public DependencyGraph(int loopId) {
		this.loopId = loopId;
		this.nodes = new ArrayList<Node>();
	}
	
	public <T extends Node> void add(T node) {
		
	}
	
	public int getNodeCount() {
		return nodes.size();
	}
}
