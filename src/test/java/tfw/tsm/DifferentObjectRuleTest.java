package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 */
class DifferentObjectRuleTest {
    @Test
    void testIsChange() {
        StateChangeRule rule = DifferentObjectRule.RULE;
        Object currentState = new Object();
        Object newState = new Object();
        assertTrue(rule.isChange(currentState, newState), "Different state");
        assertFalse(rule.isChange(currentState, currentState), "Same state");
        assertTrue(rule.isChange(null, newState), "Null currentState");
        try {
            rule.isChange(currentState, null);
            fail("isChange() accepted null new state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }
}
