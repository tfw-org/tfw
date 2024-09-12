package tfw.value;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 */
class NullConstraintTest {
    @Test
    void testIsCompatable() {
        NullConstraint nc = NullConstraint.INSTANCE;

        try {
            nc.isCompatible(null);
            fail("isCompatible() accepted null constraint");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        assertTrue(nc.isCompatible(nc), "isCompatible() rejected itself");
        assertFalse(nc.isCompatible(new IntegerConstraint(0, 1)), "isCompatible() accepted an IntegerConstaint");
    }

    @Test
    void testgetValueCompliance() {
        NullConstraint nc = NullConstraint.INSTANCE;

        try {
            nc.checkValue(new Object());
            fail("getValueCompliance() accepted null constraint");
        } catch (ValueException expected) {
            // System.out.println(expected);
        }
        try {
            nc.checkValue(null);
        } catch (ValueException unexpected) {
            fail("checkValue() didn't accept null");
        }
    }
}
