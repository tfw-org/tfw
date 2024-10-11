package tfw.tsm;

import java.util.ArrayList;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;

public class RootBuilder {
    private String name = null;
    private TransactionQueue transactionQueue = new BasicTransactionQueue();
    private CheckDependencies checkDependencies = new DefaultCheckDependencies();
    private boolean logging = false;
    private List<EventChannelDescription> eventChannelDescriptions = new ArrayList<>();
    private List<Object> initialState = new ArrayList<>();
    private TransactionExceptionHandler transactionExceptionHandler = null;

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

    public RootBuilder setTransactionExceptionHandler(final TransactionExceptionHandler transactionExceptionHandler) {
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
