package tfw.tsm;

import tfw.check.Argument;

/**
 * Factory for creating a {@link Root}.
 */
public class RootFactory extends BaseBranchFactory
{
    private boolean logging = false;

    private TransactionExceptionHandler handler = null;
    
    public Root create(String name, TransactionQueue queue) {
    	return create(name, queue, new DefaultCheckDependencies());
    }

    /**
     * Creates a root with the given name and values previously specified. Note
     * that this method calls {@link #clear()}.
     * 
     * @param name
     *            The name of the root.
     * @return a new root.
     */
    public Root create(String name, TransactionQueue queue, CheckDependencies checkDependencies)
    {
        Argument.assertNotNull(name, "name");
        TransactionMgr mgr = new TransactionMgr(queue, checkDependencies, logging);

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
