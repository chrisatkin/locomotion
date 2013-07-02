package uk.ac.ed.inf.icsa.locomotion.exception;

public class LocomotionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocomotionException(String message) {
		super(message);
	}
	
	public LocomotionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
