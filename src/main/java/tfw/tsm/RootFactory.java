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
 * without even the implied warranty of
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

import tfw.check.Argument;

/**
 * Factory for creating a {@link Root}.
 */
public class RootFactory extends BaseBranchFactory
{
    private boolean logging = false;

    private TransactionExceptionHandler handler = null;

    /**
     * Creates a root with the given name and values previously specified. Note
     * that this method calls {@link #clear()}.
     * 
     * @param name
     *            The name of the root.
     * @return a new root.
     */
    public Root create(String name, TransactionQueue queue)
    {
        Argument.assertNotNull(name, "name");
        TransactionMgr mgr = new TransactionMgr(queue, logging);

        if (handler != null)
        {
            mgr.setExceptionHandler(handler);
        }
        Root root = new Root(name, getTerminators(), mgr);
        this.clear();
        return root;
    }

    /**
     * Sets the transaction logging state.
     * 
     * @param logging
     *            flag indicating whether logging is to be turned on (<code>true</code>)
     *            or off(<code>false</code>).
     */
    public void setLogging(boolean logging)
    {
        this.logging = logging;
    }

    /**
     * Sets the exception handler for the roots transaction manager. This method
     * will over-write any previously set handlers. If an un-handle exception
     * reaches the transaction manager the
     * {@link TransactionExceptionHandler#handle(Exception)} will be called.
     * 
     * @param handler
     *            the exception handler.
     */
    public final void setTransactionExceptionHandler(
            TransactionExceptionHandler handler)
    {
        Argument.assertNotNull(handler, "handler");
        this.handler = handler;
    }
}
