package tfw.tsm;

import tfw.check.Argument;

/**
 * A state change rule based on whether the new state is a different object or
 * not.
 */
public class DifferentObjectRule implements StateChangeRule {
    /** An instance of the rule. */
    public static final DifferentObjectRule RULE = new DifferentObjectRule();

    /**
     * Hide constructor to avoid un-needed object creation. This rule is
     * stateless and therefore only one instance is needed.
     */
    private DifferentObjectRule() {}

    /**
     * Returns an instance of the rule.
     *
     * @return an instance of the rule.
     */
    public static DifferentObjectRule getInstance() {
        return RULE;
    }

    /**
     * Returns true if the new state is the same object as the current state,
     * otherwise returns false.
     *
     * @param currentState
     *            the current state of the event channel. A <code>null</code>
     *            value is allowed as the initial state.
     * @param newState
     *            the new state for the event channel. May not be
     *            <code>null</code>.
     * @return <code>currentState != newState</code>.
     * @throws IllegalArgumentException
     *             if <code>newSate == null</code>.
     */
    @Override
    public boolean isChange(Object currentState, Object newState) {
        Argument.assertNotNull(newState, "newState");
        return currentState != newState;
    }
}
