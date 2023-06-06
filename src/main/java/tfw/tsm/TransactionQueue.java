package tfw.tsm;

/**
 * Defines a queue for dispatching events which initiate transactions within the
 * framework.
 */
public interface TransactionQueue {
    /**
     * Adds a new runnable to the queue to be run later. This method must be
     * thread safe.
     *
     * @param runnable
     *            the runnable to be added to the queue.
     */
    public void invokeLater(Runnable runnable);

    /**
     * Adds a runnable to the queue and waits until the runnable executes before
     * returning.
     *
     * @param runnable
     *            The runnable to be executed.
     *
     * @throws Error
     *             if called from the dispatch thread of the queue.
     */
    public void invokeAndWait(Runnable runnable) throws Exception;

    /**
     * Returns true if the calling thread is the current transaction thread.
     *
     * @return true if the calling thread is the current transaction thread.
     */
    public boolean isDispatchThread();

    public void lock();

    public void unlock();

    public TransactionState createTransactionState();
}
