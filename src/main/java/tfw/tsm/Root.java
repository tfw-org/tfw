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
    public interface Builder {
        Builder setName(final String name);

        Builder setTransactionQueue(final TransactionQueue transactionQueue);

        Builder setCheckDependencies(final CheckDependencies checkDependencies);

        Builder setLogging(final boolean logging);

        Builder addEventChannel(EventChannelDescription eventChannelDescription, Object initialState);

        Builder setTransactionExceptionHandler(final TransactionExceptionHandler transactionExceptionHandler);

        Root create();
    }

    private final TransactionMgr transactionMgr;

    /**
     * Creates a root with the specified event channels and ports.
     * @param name
     * @param terminator
     * @param ports
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

    public static Builder builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl implements Builder {
        private String name = null;
        private TransactionQueue transactionQueue = new BasicTransactionQueue();
        private CheckDependencies checkDependencies = new DefaultCheckDependencies();
        private boolean logging = false;
        private List<EventChannelDescription> eventChannelDescriptions = new ArrayList<>();
        private List<Object> initialState = new ArrayList<>();
        private TransactionExceptionHandler transactionExceptionHandler = null;

        @Override
        public Builder setName(final String name) {
            this.name = name;

            return this;
        }

        @Override
        public Builder setTransactionQueue(final TransactionQueue transactionQueue) {
            this.transactionQueue = transactionQueue;

            return this;
        }

        @Override
        public Builder setCheckDependencies(final CheckDependencies checkDependencies) {
            this.checkDependencies = checkDependencies;

            return this;
        }

        @Override
        public Builder setLogging(final boolean logging) {
            this.logging = logging;

            return this;
        }

        @Override
        public Builder addEventChannel(EventChannelDescription eventChannelDescription, Object initialState) {
            this.eventChannelDescriptions.add(eventChannelDescription);
            this.initialState.add(initialState);

            return this;
        }

        @Override
        public Builder setTransactionExceptionHandler(final TransactionExceptionHandler transactionExceptionHandler) {
            this.transactionExceptionHandler = transactionExceptionHandler;

            return this;
        }

        @Override
        public Root create() {
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
