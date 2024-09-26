package tfw.tsm;

import tfw.check.Argument;

/**
 * A state change rule, based on the <code>equals()</code> method
 */
public class DotEqualsRule implements StateChangeRule {

    /** An instance of the rule. */
    public static final DotEqualsRule RULE = new DotEqualsRule();

    /**
     * Hide constructor to avoid un-needed object creation. This rule is
     * stateless and therefore only one instance is needed.
     */
    private DotEqualsRule() {}

    /**
     * Returns an instance of the rule.
     * @return an instance of the rule.
     */
    public static DotEqualsRule getInstance() {
        return RULE;
    }

    /**
     * Returns true if the new state is different from the current state based
     * on the equals method, otherwise returns false.
     * @return <code>!newState.equals(currentState)</code>.
     * @throws IllegalArgumentException if <code>newState == null</code>
     */
    @Override
    public boolean isChange(Object currentState, Object newState) {
        Argument.assertNotNull(newState, "newState");
        return !newState.equals(currentState);
    }
}
