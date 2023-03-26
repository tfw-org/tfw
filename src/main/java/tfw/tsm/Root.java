package tfw.tsm;




/**
 * The base of the event channel communications structure. All event channels
 * which reach the <code>Root<\code> must be terminated at the root or an
 * exception will be thrown.
 *
 */
public class Root extends Branch
{
    private final TransactionMgr transactionMgr;

    /**
     * Creates a trivial root with no event channels. To create a root with
     * event channels use {@link RootFactory}.
     *
     * @param name the name of the root.
     */
    public Root(String name)
    {
        this(name, null);
    }

	/**
	 * Creates a root with the specified attriubtes and an asynchronous
	 * AWT transaction queue.
	 * 
	 * @param name the name of the root.
	 * @param terminators a map of terminators.
	 * @param ports a set of ports.
	 */
    Root(String name, EventChannel[] eventChannels)
    {
        this(name, eventChannels,
            new TransactionMgr(new AWTTransactionQueue(), false));
    }

    /**
     * Creates a root with the specified event channels and ports.
     * @param name
     * @param terminator
     * @param ports
     */
    Root(String name, EventChannel[] eventChannels,
        TransactionMgr transactionMgr)
    {
        super(name, null, null, eventChannels);
        this.transactionMgr = transactionMgr;
        this.immediateRoot = this;
    }
    
    /**
     * Returns the {@link TransactionMgr} for this tree.
     * @return the {@link TransactionMgr} for this tree.
     */
    TransactionMgr getTransactionManager(){
    	return transactionMgr;
    }

    /**
     * Returns true.
     * @return true.
     */
    public final boolean isRooted()
    {
        return true;
    }
    
    public boolean isLogging()
    {
    	return(transactionMgr.isLogging());
    }
    
    public void setLogging(boolean logging)
    {
    	transactionMgr.setLogging(logging);
    }
    
    public static final void setTraceLogging(boolean traceLogging)
    {
    	TransactionMgr.setTraceLogging(traceLogging);
    }
    
    public void setLocationFormatter(
    	TransactionMgr.LocationFormatter locationFormatter)
    {
    	transactionMgr.setLocationFormatter(locationFormatter);
    }
    
//    /**
//     * Sets the exception handler for this roots transaction manager. This
//     * method will over-write any previously set handlers. If an un-handle 
//     * exception reaches the transaction manager the 
//     * {@link TransactionExceptionHandler#handle(Exception)} will be called.
//     * 
//     * @param handler the exception handler.
//     */
//    public final void setTransactionExceptionHandler(TransactionExceptionHandler handler){
//    	CheckArgument.checkNull(handler, "handler");
//    	this.transactionMgr.setExceptionHandler(handler);
//    }
}
