package tfw.tsm;

/**
 * Represents a problem with termination, such as a sink or source
 * which is in compatible with the event channel value constraint.
 */
class TerminatorException extends RuntimeException {
	
	/**
	 * Creates an exception with the specified message.
	 * @param message the exception message
	 */
	public TerminatorException(String message){
		super(message);
	}
	
	/**
	 * Creates an exception with the specified attributes.
	 * @param message the exception message
	 * @param cause the cause.
	 */
	public TerminatorException(String message, Throwable cause){
		super(message, cause);
	}
}
