package tfw.tsm;

import java.util.Arrays;
import java.util.HashSet;
import tfw.check.Argument;
import tfw.tsm.ecd.ECDUtility;
import tfw.tsm.ecd.EventChannelDescription;

/**
 * The base class for commit components
 */
abstract class BaseCommit extends EventHandler {
    private final HashSet<String> triggers;
    private final Initiator[] initiators;
    private CommitRollbackListener crListener = new CommitRollbackListener() {
        @Override
        public void rollback() {}

        @Override
        public void commit() {
            if (isStateNonNull()) {
                BaseCommit.this.commit();
            } else {
                BaseCommit.this.debugCommit();
            }
        }

        @Override
        public String getName() {
            return BaseCommit.this.getName();
        }
    };

    BaseCommit(
            String name,
            EventChannelDescription[] triggerSinks,
            EventChannelDescription[] nonTriggerSinks,
            Initiator[] initiators) {
        super(
                name,
                checkTriggers(triggerSinks, "triggerSinks"),
                checkNonTriggers(nonTriggerSinks, "nonTriggerSinks"),
                null);
        Argument.assertNotEmpty(triggerSinks, "triggerSinks");

        if (initiators != null) {
            Argument.assertElementNotNull(initiators, "initiators");
            this.initiators = initiators.clone();
        } else {
            this.initiators = new Initiator[0];
        }

        this.triggers = new HashSet<String>(Arrays.asList(ECDUtility.toEventChannelNames(triggerSinks)));
    }

    private static EventChannelDescription[] checkTriggers(EventChannelDescription[] pd, String name) {
        Argument.assertElementNotNull(pd, name);

        return pd;
    }

    private static EventChannelDescription[] checkNonTriggers(EventChannelDescription[] pd, String name) {
        if (pd != null) {
            Argument.assertElementNotNull(pd, name);
        }

        return pd;
    }

    /**
     * Returns true if the specified sink event channel state has changed
     * during the current transaction, otherwise returns
     * false. Note that <code>equals()</code> is used to determine if the
     * state has changed.
     *
     * @param sinkEventChannel the name of the event channel to check.
     * @return true if the specified sink event channel state has changed
     * during the current transaction state change cycle, otherwise returns
     * false.
     */
    protected final boolean isStateChanged(EventChannelDescription sinkEventChannel) {
        // TODO: change this to use the state change rule.
        Sink sink = getSink(sinkEventChannel);

        if (sink == null) {
            throw new IllegalArgumentException(sinkEventChannel + " not found");
        }

        return sink.eventChannel.isStateChanged();
    }

    /**
     * Adds this commit component to the transaction manager if the source
     * of the state change is not one of this commit's initiator and the event
     * channel is one of this commit's trigger ports.
     */
    @Override
    void stateChange(EventChannel eventChannel) {
        for (int i = 0; i < initiators.length; i++) {
            Source source = initiators[i].getSource(eventChannel.getECD().getEventChannelName());

            if (source == eventChannel.getCurrentStateSource()) {
                return;
            }
        }

        if (triggers.contains(eventChannel.getECD().getEventChannelName())) {
            getTransactionManager().addCommitRollbackListener(crListener);
        }
    }

    /**
     * This method is called during the commit phase of a transaction in which
     * one or more of the triggering event channels has its state changed and
     * all of the dependent event channels have non-null state.
     */
    protected abstract void commit();

    /**
     * This method is called during the commit phase of a transaction in which
     * one or more of the triggering event channels has its state changed and
     * one or more of the dependent event channels has null state.
     */
    protected void debugCommit() {}
}
