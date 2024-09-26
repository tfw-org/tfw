package tfw.tsm;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import tfw.check.Argument;

/**
 * An asynchronous transaction queue which uses the AWT thread. This queue
 * should be used for any transaction management involving AWT or Swing
 * components. It ensures that all state changes occur in the AWT thread and is
 * therefore safe to use with GUI components.
 */
public class AWTTransactionQueue implements TransactionQueue {

    /**
     * Adds the runnable to the AWT event queue. The runnable will be run
     * asynchronously.
     *
     * @param runnable to be added to the queue.
     */
    @Override
    public void invokeLater(Runnable runnable) {
        Argument.assertNotNull(runnable, "runnable");
        EventQueue.invokeLater(runnable);
    }

    @Override
    public void invokeAndWait(Runnable runnable) throws InvocationTargetException, InterruptedException {
        Argument.assertNotNull(runnable, "runnable");
        EventQueue.invokeAndWait(runnable);
    }

    /**
     * Returns true if the calling thread is the current AWT
     * <code>EventQueue's</code> dispatch thread.
     *
     * @return true if the calling thread is the current AWT
     *         <code>EventQueue's</code> dispatch thread.
     */
    @Override
    public boolean isDispatchThread() {
        return EventQueue.isDispatchThread();
    }

    private final Lock transactionQueueLock = new ReentrantLock();

    @Override
    public void lock() {
        transactionQueueLock.lock();
    }

    @Override
    public void unlock() {
        transactionQueueLock.unlock();
    }

    @Override
    public TransactionState createTransactionState() {
        return new TransactionStateImpl();
    }
}
