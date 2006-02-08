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

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import tfw.check.Argument;

/**
 * An asynchronous transaction queue which uses the AWT thread. This queue
 * should be used for any transaction management involving AWT or Swing
 * components. It ensures that all state changes occur in the AWT thread and is
 * therefore safe to use with GUI components.
 */
public class AWTTransactionQueue implements TransactionQueue
{

    /**
     * Adds the runnable to the AWT event queue. The runnable will be run
     * asynchronously.
     * 
     * @param the
     *            runnable to added to the queue.
     */
    public void invokeLater(Runnable runnable)
    {
        Argument.assertNotNull(runnable, "runnable");
        EventQueue.invokeLater(runnable);
    }

    public void invokeAndWait(Runnable runnable)
            throws InvocationTargetException, InterruptedException
    {
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
    public boolean isDispatchThread()
    {
        return EventQueue.isDispatchThread();
    }

}
