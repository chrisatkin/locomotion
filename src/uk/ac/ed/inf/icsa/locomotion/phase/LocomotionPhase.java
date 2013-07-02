package uk.ac.ed.inf.icsa.locomotion.phase;

import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.phases.Phase;

public abstract class LocomotionPhase extends Phase {		
	@Override
	abstract protected void run(StructuredGraph graph);
}
