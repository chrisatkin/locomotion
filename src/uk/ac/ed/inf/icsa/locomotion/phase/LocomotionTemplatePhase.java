package uk.ac.ed.inf.icsa.locomotion.phase;

import com.oracle.graal.api.code.TargetDescription;
import com.oracle.graal.api.meta.MetaAccessProvider;
import com.oracle.graal.nodes.spi.Replacements;

abstract class LocomotionTemplatePhase extends LocomotionPhase {

	protected MetaAccessProvider runtime;
	protected Replacements replacements;
	protected TargetDescription target;
	
	protected LocomotionTemplatePhase(MetaAccessProvider runtime, Replacements replacements, TargetDescription target) {
		this.runtime = runtime;
		this.replacements = replacements;
		this.target = target;
	}

}
