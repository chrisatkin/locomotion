package uk.ac.ed.inf.icsa.locomotion.exceptions;

import uk.ac.ed.inf.icsa.locomotion.instrumentation.Access;

public class LoopDependencyException extends LocomotionException {
	public enum DependencyKind {
		ReadWrite,
		WriteRead,
		WriteWrite
		// we are unconcerned with read-read
	}
	
	private static final long serialVersionUID = 5046561957950653407L;
	private Access access;
	private int iteration1;
	private int iteration2;
	private DependencyKind kind;
	
	public LoopDependencyException(Access access, int iteration1, int iteration2, DependencyKind kind) {
		super("kind=" + kind.toString() + " this=" + iteration1 + " other=" + iteration2 + " access=" + access.toString());
		this.access = access;
		this.iteration1 = iteration1;
		this.iteration2 = iteration2;
		this.kind = kind;
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
	
	public DependencyKind getKind() {
		return kind;
	}
}