package tfw.tsm;

/**
 * A state change rule for always notifying sinks when state is published.
 */
public class AlwaysChangeRule implements StateChangeRule {
    /** An instance of the rule. */
    public static final AlwaysChangeRule RULE = new AlwaysChangeRule();

    /**
     * Hide constructor to avoid un-needed object creation. This rule is
     * stateless and therefore only one instance is needed.
     */
    private AlwaysChangeRule() {}

    /**
     * Returns an instance of the rule.
     *
     * @return an instance of the rule.
     */
    public static AlwaysChangeRule getInstance() {
        return RULE;
    }

    /**
     * Returns <code>true</code> reqardless of the specified values.
     *
     * @param currentState
     *            the current event channel state.
     * @param newState
     *            the new state value for the event channel.
     * @return <code>true</code> reqardless of the specified values.
     */
    public boolean isChange(Object currentState, Object newState) {
        return true;
    }
}
