package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

/**
 *
 */
class InitiatorSource extends Source {
    private final StateQueue stateQueue;

    InitiatorSource(String name, EventChannelDescription ecd, StateQueue stateQueue) {
        super(name, ecd);
        this.stateQueue = stateQueue;
    }

    /**
     * Connects this <code>Source</code> to the specified event channel. If
     * the deferred state is set, the <code>eventChannel</code> will be set
     * the deferred state asynchronously. This method should only be called
     * during the branch construction phase of a transaction.
     *
     * @param eventChannel
     *            the event channel for this <code>Source</code>.
     */
    public synchronized void setEventChannel(EventChannel eventChannel) {
        /*
         * TODO: This needs to be re-thought. It was put here to make sure the
         * deferred state of an initiator gets fired after it has been removed.
         */
        if (eventChannel == null) {
            return;
        }
        super.setEventChannel(eventChannel);
    }

    /**
     * This method asynchronously causes the event channel state to be changed
     * to the specified state value. If this <code>Source</code> is not
     * connected to an event channel it will cause a state change to the event
     * channel when it is connected.
     *
     * @param state
     *            the new event channel value.
     */
    synchronized void setState(Object state) throws ValueException {
        if (!this.getTreeComponent().isRooted()) {
            throw new IllegalStateException(
                    "Attempt to set state on disconnected event channel (" + eventChannel.getECD() + ").");
        }
        ecd.getConstraint().checkValue(state);
        this.stateQueue.push(new EventChannelNState(this.eventChannel, state));
    }

    /**
     * Sets the state of the event channel. This method should only be called by
     * {@link TransactionMgr}.
     */
    synchronized Object fire() {
        if (!stateQueue.isEmpty()) {
            EventChannelNState ecs = (EventChannelNState) stateQueue.pop();
            TreeComponent component = ecs.ec.getParent();
            /*
             * if the event channel is rooted and is rooted to the same
             * transaction manager as is running the current transaction, then
             * set the state on the event channel.
             */
            if (component != null
                    && component.isRooted()
                    && component.getTransactionManager().queue.isDispatchThread()) {
                ecs.ec.setState(this, ecs.state, null);
                return ecs.state;
            }
        }
        return null;
    }

    private class EventChannelNState {
        private final EventChannel ec;

        private final Object state;

        public EventChannelNState(EventChannel ec, Object state) {
            this.ec = ec;
            this.state = state;
        }
    }
}
