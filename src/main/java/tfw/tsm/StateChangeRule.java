package tfw.tsm;

/**
 * Defines the rule for what constitutes a state change on an event channel.
 * Notification of a state change will only occur if
 * {@link #isChange(Object, Object)}returns <code>true</code>. A state
 * change rule can be set when an event channel is terminated at the root or branch
 * level. See {@link RootFactory} and {@link BranchFactory}.
 */
public interface StateChangeRule {
    /**
     * Returns true if the new state represents a change from the current state
     *
     * @param currentState
     *            the current state of the event channel. A <code>null</code>
     *            value is allowed as the initial state.
     * @param newState
     *            the new state for the event channel. May not be
     *            <code>null</code>.
     * @return <code>true</code> if <code>newState</code> represents a
     *         change relative to the <code>currentState</code>.
     * @throws IllegalArgumentException
     *             if <code>newSate == null</code>.
     */
    boolean isChange(Object currentState, Object newState);
}
