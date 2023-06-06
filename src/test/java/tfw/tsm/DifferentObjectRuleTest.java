package tfw.tsm;

import junit.framework.TestCase;

/**
 *
 */
public class DifferentObjectRuleTest extends TestCase {
    public void testIsChange() {
        StateChangeRule rule = DifferentObjectRule.RULE;
        Object currentState = new Object();
        Object newState = new Object();
        assertTrue("Different state", rule.isChange(currentState, newState));
        assertFalse("Same state", rule.isChange(currentState, currentState));
        assertTrue("Null currentState", rule.isChange(null, newState));
        try {
            rule.isChange(currentState, null);
            fail("isChange() accepted null new state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }
}
