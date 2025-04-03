package tfw.tsm;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.RollbackECD;

/**
 * The base class for all event handling components which participate in
 * transaction rollbacks.
 */
abstract class RollbackHandler extends EventHandler {
    /**
     * Creates a rollback event handler with the specified attributes.
     *
     * @param name the name of the component.
     * @param triggeringSinks the set of triggering input event channel descriptions.
     * @param nonTriggeringSinks the set of non-triggering input event channel descriptions.
     * @param sourceDescriptions the set of output event channel descriptions.
     */
    RollbackHandler(
            String name,
            EventChannelDescription[] triggeringSinks,
            EventChannelDescription[] nonTriggeringSinks,
            EventChannelDescription[] sourceDescriptions) {
        super(name, triggeringSinks, nonTriggeringSinks, sourceDescriptions);
    }

    /**
     * Causes a rollback of the current transaction. All event channels which
     * participate in rollbacks will be set back to their state prior to the
     * initiation of the current transaction.
     */
    protected final void rollback() {
        throw new RollbackException();
    }

    /**
     * Causes a rollback of the current transaction and sets the state of the
     * specified event channel in a follow-on transaction.
     *
     * @param ecd
     *            The event channel on which the state is to be set.
     * @param state
     *            The state for the event channel.
     */
    protected final void rollback(RollbackECD ecd, Object state) {
        Argument.assertNotNull(ecd, "ecd");
        Argument.assertNotNull(state, "state");
        initiatorSet(ecd, state);
        rollback();
    }

    /**
     * Causes a rollback of the current transaction and sets the state of the
     * specified event channels in a follow-on transaction.
     *
     * @param eventChannelState
     *            The event channels and state to be set in a follow-on
     *            transaction.
     */
    protected final void rollback(EventChannelState[] eventChannelState) {
        Argument.assertNotNull(eventChannelState, "eventChannelState");
        Argument.assertElementNotNull(eventChannelState, "eventChannelState");
        initiatorSet(eventChannelState);
        rollback();
    }
}
