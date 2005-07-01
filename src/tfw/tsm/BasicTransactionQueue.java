/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm;


import java.util.ArrayList;

import tfw.check.Argument;


/**
 * A basic transaction queue and event dispatch thread. This queue should
 * not be used with AWT or Swing compoents. Use the {@link AWTTransactionQueue}
 * for AWT and Swing components.
 */
public final class BasicTransactionQueue implements TransactionQueue
{
    /** The default name for the transaction queue thread. */
    public static final String DEFAULT_THREAD_NAME = "BasicTransactionQueue";
    private final ArrayList queue = new ArrayList();
    private Thread thread = null;
    private final String threadName;

    /**
     * Creates a transaction queue with the specified thread name.
     * @param threadName the name assigned to the transaction queue thread.
     */
    public BasicTransactionQueue(String threadName)
    {
        Argument.assertNotNull(threadName, "threadName");
        this.threadName = threadName;
    }

    /**
     * Creates a transaction queue with the default thread name,
     * {@link #DEFAULT_THREAD_NAME}.
     */
    public BasicTransactionQueue()
    {
        this.threadName = DEFAULT_THREAD_NAME;
    }

    /**
     * Adds the specified runnable to the transaction queue.
     * @param runnable the runnable to add to the queue.
     */
    public final synchronized void add(Runnable runnable)
    {
        Argument.assertNotNull(runnable, "runnable");
        queue.add(runnable);
        checkThread();
    }

    /**
     * Creates a thread if one is needed. This method must be
     * called from a synchronized context.
     */
    private void checkThread()
    {
        if (queue.size() == 0)
        {
            return;
        }

        if (thread == null)
        {
            thread = new Thread(new QueueThreadRunnable(), threadName);
            thread.start();
        }
    }

    /**
     * Returns <code>true</code> if the queue is empty, otherwise returns
     * <code>false</code>.
     * @return <code>true</code> if the queue is empty, otherwise returns
     * <code>false</code>.
     */
    public final synchronized boolean isEmpty()
    {
        return thread == null;
    }

    /**
     * Returns <code>true</code> if the calling thread is the current
     * transaction queue thread.
     * @return <code>true</code> if the calling thread is the current
     * transaction queue thread.
     */
    public final synchronized boolean isDispatchThread()
    {
        if ((thread != null) && (Thread.currentThread() == thread))
        {
            return (true);
        }

        return (false);
    }

    /**
     * Interrupts the thread associated with this queue.
     *
     * @throws SecurityException if the current thread cannot modify the
     * transaction queue thread
     */
    public final synchronized void interrupt()
    {
        if (thread != null)
        {
            thread.interrupt();
            thread = null;
        }
    }

    /**
     * Blocks the calling thead until the transaction queue is empty. This
     * method must not be called from within this transaction queue's thread.
     * Use {@link #isDispatchThread} to check whether the calling thread is
     * the transaction queue thread.
     */
    public void waitTilEmpty()
    {
        if (isDispatchThread())
        {
            throw new IllegalStateException(
                "This method can not be called from within the queue's thread");
        }

        while (!isEmpty())
        {
            try
            {
                Thread.sleep(2);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * The runnable for executing the transaction runnables.
     */
    private class QueueThreadRunnable implements Runnable
    {
        public void run()
        {
            while (true)
            {
                Runnable r = null;

                synchronized (BasicTransactionQueue.this)
                {
                    if (queue.size() == 0)
                    {
                        thread = null;

                        return;
                    }

                    r = (Runnable) queue.remove(0);
                }

                try
                {
                    r.run();
                }
                catch (RuntimeException e)
                {
                    synchronized (BasicTransactionQueue.this)
                    {
                        thread = null;
                        checkThread();
                    }

                    throw e;
                }
            }
        }
    }
}
