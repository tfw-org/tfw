package tfw.tsm;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import tfw.check.Argument;

/**
 * A basic transaction queue and event dispatch thread. This queue should not be
 * used with AWT or Swing components. Use the {@link AWTTransactionQueue} for AWT
 * and Swing components.
 */
public final class BasicTransactionQueue implements TransactionQueue {
    /** The default name for the transaction queue thread. */
    public static final String DEFAULT_THREAD_NAME = "BasicTransactionQueue";

    private final ArrayList<Runnable> queue = new ArrayList<Runnable>();

    private Thread thread = null;

    private final String threadName;

    /**
     * Creates a transaction queue with the specified thread name.
     *
     * @param threadName
     *            the name assigned to the transaction queue thread.
     */
    public BasicTransactionQueue(String threadName) {
        Argument.assertNotNull(threadName, "threadName");
        this.threadName = threadName;
    }

    /**
     * Creates a transaction queue with the default thread name,
     * {@link #DEFAULT_THREAD_NAME}.
     */
    public BasicTransactionQueue() {
        this.threadName = DEFAULT_THREAD_NAME;
    }

    /**
     * Adds the specified runnable to the transaction queue.
     *
     * @param runnable
     *            the runnable to add to the queue.
     */
    public synchronized void invokeLater(Runnable runnable) {
        Argument.assertNotNull(runnable, "runnable");
        queue.add(runnable);
        checkThread();
    }

    private static class InvokeAndWaitRunnable implements Runnable {
        private final Runnable runnable;

        private final Object lock;

        public InvokeAndWaitRunnable(Runnable runnable, Object lock) {
            this.runnable = runnable;
            this.lock = lock;
        }

        public void run() {
            synchronized (lock) {
                try {
                    this.runnable.run();
                } finally {
                    lock.notify();
                }
            }
        }
    }

    public void invokeAndWait(Runnable runnable) throws InterruptedException {
        Argument.assertNotNull(runnable, "runnable");
        if (isDispatchThread()) {
            runnable.run();
            return;
        }
        class InvokeAndWaitLock {}
        InvokeAndWaitLock lock = new InvokeAndWaitLock();
        InvokeAndWaitRunnable iwr = new InvokeAndWaitRunnable(runnable, lock);
        synchronized (lock) {
            invokeLater(iwr);
            lock.wait();
        }
    }

    /**
     * Creates a thread if one is needed. This method must be called from a
     * synchronized context.
     */
    private void checkThread() {
        if (queue.size() == 0) {
            return;
        }

        if (thread == null) {
            thread = new Thread(new QueueThreadRunnable(), threadName);
            thread.start();
        }
    }

    /**
     * Returns <code>true</code> if the queue is empty, otherwise returns
     * <code>false</code>.
     *
     * @return <code>true</code> if the queue is empty, otherwise returns
     *         <code>false</code>.
     */
    public synchronized boolean isEmpty() {
        return thread == null;
    }

    /**
     * Returns <code>true</code> if the calling thread is the current
     * transaction queue thread.
     *
     * @return <code>true</code> if the calling thread is the current
     *         transaction queue thread.
     */
    public synchronized boolean isDispatchThread() {
        if (thread != null && Thread.currentThread() == thread) {
            return true;
        }

        return false;
    }

    /**
     * Interrupts the thread associated with this queue.
     *
     * @throws SecurityException
     *             if the current thread cannot modify the transaction queue
     *             thread
     */
    public synchronized void interrupt() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    /**
     * Blocks the calling thead until the transaction queue is empty. This
     * method must not be called from within this transaction queue's thread.
     * Use {@link #isDispatchThread} to check whether the calling thread is the
     * transaction queue thread.
     */
    public void waitTilEmpty() {
        if (isDispatchThread()) {
            throw new IllegalStateException("This method can not be called from within the queue's thread");
        }

        while (!isEmpty()) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * The runnable for executing the transaction runnables.
     */
    private class QueueThreadRunnable implements Runnable {
        public void run() {
            while (true) {
                Runnable r = null;

                synchronized (BasicTransactionQueue.this) {
                    if (queue.size() == 0) {
                        thread = null;

                        return;
                    }

                    r = queue.remove(0);
                }

                try {
                    r.run();
                } catch (RuntimeException e) {
                    synchronized (BasicTransactionQueue.this) {
                        thread = null;
                        checkThread();
                    }

                    throw e;
                }
            }
        }
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
