package tfw.tsm;

public interface TransactionState {
    /**
     * Gets a <code>Future&lt;?&gt;</code> which represents the future result
     * of the transaction.
     */
    public TfwFuture<Throwable> getResultFuture();

    /**
     * Gets a <code>Future&lt;Long&gt;</code> whose result is the ID of the
     * transaction.  This value is ready when the transaction has been enqueued.
     * @return the future id of the transaction.
     */
    public TfwFuture<Long> getTransactionIdFuture();
}
