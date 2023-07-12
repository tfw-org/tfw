package tfw.tsm;

/**
 * Handler for processing uncaught exceptions that occur during the execution
 * of a transaction. To set the handler see
 * {@link RootFactory#setTransactionExceptionHandler(TransactionExceptionHandler)}
 */
public interface TransactionExceptionHandler {
    /**
     * Called by the transaction manager when an un-caught exception occurs
     * during a transaction.
     *
     * @param exception
     *            an uncaught exception thrown during a transaction.
     */
    void handle(Exception exception);
}
