package tfw.tsm;

import tfw.tsm.ecd.EventChannelDescription;

/**
 * An interface which defines the services an event channel must provide.
 */
public interface EventChannel {
    /**
     * Returns the non_name of this event channel
     *
     * @return the non-null name for this event channel.
     */
    EventChannelDescription getECD();

    /**
     * Sets the component associated with this event channel.
     *
     * @param component
     *            the component associated with this event channel.
     */
    void setTreeComponent(TreeComponent component);

    /**
     * Connects a {@link Port} to the event channel.
     *
     * @param port the port to be connected.
     */
    void add(Port port);

    /**
     * Disconnects a {@link Port} from this event channel.
     *
     * @param port the port to be disconnected.
     */
    void remove(Port port);

    /**
     * Get the parent of this event channel.
     *
     * @return The event channels parent
     */
    TreeComponent getParent();

    /**
     * Returns the current state of the event channel.
     */
    Object getState();

    /**
     * Returns the state of this event channel prior to the current state change
     * cycle.
     */
    Object getPreviousCycleState();

    /**
     * Returns the state of this event channel prior to the current state change
     * cycle.
     */
    Object getPreviousTransactionState();

    /**
     * @return True if new state has been published during the current
     *         transaction. Otherwise, false if the state has not changed or if
     *         no transaction is in progress.
     */
    boolean isStateChanged();

    /**
     * Sets the state of this event channel.
     *
     * @param state
     *            the new value.
     */
    void setState(Source source, Object state, EventChannel forwardingEventChannel);

    /**
     * Returns the Source which set the current state.
     */
    Source getCurrentStateSource();

    /**
     * Sets the previous cycle state to the current state.
     */
    void synchronizeCycleState();

    /**
     * Sets the previous transaction state to the current state.
     */
    void synchronizeTransactionState();

    /**
     * Nofies any uninitialized sinks of the current state.
     */
    Object fire();

    /**
     * Returns true if the <code>EventChannel</code> fires on connect.
     */
    boolean isFireOnConnect();

    /**
     * Returns true if the <code>EventChannel</code> participates in
     * rollbacks. An <code>EventChannel</code> that participates in rollbacks
     * is one that guarentees that its state will be set back to it's value
     * prior to the beginning of the transaction in which a rollback occurs.
     */
    boolean isRollbackParticipant();

    /**
     * Adds an asynchronous state change. This method is generally called by an
     * {@link Source} to notify the event channel that it has a state change
     *
     * @param source
     *            the source with the state change.
     */
    void addDeferredStateChange(ProcessorSource source);
}
