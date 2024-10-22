package tfw.tsm;

import java.util.ArrayList;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;

/**
 * The base of the event channel communications structure. All event channels
 * which reach the Root must be terminated at the root or an
 * exception will be thrown.
 *
 */
public class Root extends Branch {
    private final TransactionMgr transactionMgr;

    /**
     * Creates a root with the specified event channels and ports.
     *
     * @param name of the Root branch
     * @param eventChannels the list of event channels associated with this root branch.
     * @param transactionMgr the transaction manager associated with this root branch.
     */
    Root(String name, EventChannel[] eventChannels, TransactionMgr transactionMgr) {
        super(name, null, null, eventChannels);
        this.transactionMgr = transactionMgr;
        this.immediateRoot = this;
    }

    /**
     * Returns the {@link TransactionMgr} for this tree.
     * @return the {@link TransactionMgr} for this tree.
     */
    @Override
    TransactionMgr getTransactionManager() {
        return transactionMgr;
    }

    /**
     * Returns true.
     * @return true.
     */
    @Override
    public final boolean isRooted() {
        return true;
    }

    public boolean isLogging() {
        return transactionMgr.isLogging();
    }

    public void setLogging(boolean logging) {
        transactionMgr.setLogging(logging);
    }

    public static final void setTraceLogging(boolean traceLogging) {
        TransactionMgr.setTraceLogging(traceLogging);
    }

    public void setLocationFormatter(TransactionMgr.LocationFormatter locationFormatter) {
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

    public static RootBuilder builder() {
        return new RootBuilder();
    }

    public static class RootBuilder {
        private String name = null;
        private TransactionQueue transactionQueue = new BasicTransactionQueue();
        private CheckDependencies checkDependencies = new DefaultCheckDependencies();
        private boolean logging = false;
        private List<EventChannelDescription> eventChannelDescriptions = new ArrayList<>();
        private List<Object> initialState = new ArrayList<>();
        private TransactionExceptionHandler transactionExceptionHandler = null;

        RootBuilder() {}

        public RootBuilder setName(final String name) {
            this.name = name;

            return this;
        }

        public RootBuilder setTransactionQueue(final TransactionQueue transactionQueue) {
            this.transactionQueue = transactionQueue;

            return this;
        }

        public RootBuilder setCheckDependencies(final CheckDependencies checkDependencies) {
            this.checkDependencies = checkDependencies;

            return this;
        }

        public RootBuilder setLogging(final boolean logging) {
            this.logging = logging;

            return this;
        }

        public RootBuilder addEventChannel(EventChannelDescription eventChannelDescription, Object initialState) {
            this.eventChannelDescriptions.add(eventChannelDescription);
            this.initialState.add(initialState);

            return this;
        }

        public RootBuilder setTransactionExceptionHandler(
                final TransactionExceptionHandler transactionExceptionHandler) {
            this.transactionExceptionHandler = transactionExceptionHandler;

            return this;
        }

        public Root build() {
            Argument.assertNotNull(name, "name");

            TransactionMgr mgr = new TransactionMgr(transactionQueue, checkDependencies, logging);

            if (transactionExceptionHandler != null) {
                mgr.setExceptionHandler(transactionExceptionHandler);
            }

            final BaseBranchFactory baseBranchFactory = new BaseBranchFactory();

            for (int i = 0; i < eventChannelDescriptions.size(); i++) {
                baseBranchFactory.addEventChannel(eventChannelDescriptions.get(i), initialState.get(i));
            }

            return new Root(name, baseBranchFactory.getTerminators(), mgr);
        }
    }
}
