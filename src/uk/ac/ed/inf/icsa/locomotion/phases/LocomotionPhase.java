package uk.ac.ed.inf.icsa.locomotion.phases;

import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.Phase;

public abstract class LocomotionPhase extends Phase {
	
	public enum InsertationPosition {
		Before,
		After
	}

	@Override
	abstract protected void run(StructuredGraph graph);
}
