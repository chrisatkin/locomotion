package uk.ac.ed.inf.icsa.locomotion.exceptions;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

public final class LoopDependencyException extends LocomotionException {
	private Access access;
	private int iteration1;
	private int iteration2;
	
	public LoopDependencyException(Access access, int iteration1, int iteration2) {
		super("this=" + iteration1 + " other=" + iteration2 + " access=" + access.toString());
		this.access = access;
		this.iteration1 = iteration1;
		this.iteration2 = iteration2;
	}
	
	public Access getAccess() {
		return access;
	}
	
	public int getIteration1() {
		return iteration1;
	}
	
	public int getIteration2() {
		return iteration2;
	}
}