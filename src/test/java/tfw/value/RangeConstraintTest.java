package tfw.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 */
class RangeConstraintTest {
    @Test
    void testConstructionNullMinimum() {
        try {
            new RangeConstraint(Integer.class, null, 10, true, true);
            fail("Constructor accepted null minimum!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testConstructionNullMaximum() {
        try {
            new RangeConstraint(Integer.class, 0, null, true, true);
            fail("Constructor accepted null maximum!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testConstructorMinGreaterThanMax() {
        try {
            new RangeConstraint(Integer.class, 10, 0, true, true);
            fail("Constructor accepted min > max!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testConstructorMinEqualMaxNeitherInclusive() {
        try {
            new RangeConstraint(Integer.class, 0, 0, false, false);
            fail("Constructor accepted min == max neither inclusive!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testIsCompatibles() {
        RangeConstraint rc = new RangeConstraint(Integer.class, 0, 2, false, false);
        assertFalse(rc.isCompatible(null), "isCompatible(null) returned true");

        RangeConstraint rcLow = new RangeConstraint(Integer.class, 0, 0, true, true);
        assertFalse(rc.isCompatible(rcLow), "isCompatible(rcLow) returned true");

        RangeConstraint rcHigh = new RangeConstraint(Integer.class, 2, 2, true, true);
        assertFalse(rc.isCompatible(rcHigh), "isCompatible(rcHigh) returned true");

        RangeConstraint rcMiddle = new RangeConstraint(Integer.class, 1, 1, true, true);
        assertTrue(rc.isCompatible(rcMiddle), "isCompatible(rcMiddle) returned false");

        assertTrue(rc.isCompatible(rc), "isCompatible(rc) returned false");

        RangeConstraint rcFloat = new RangeConstraint(Float.class, 1.0, 1.0, true, true);

        assertFalse(rc.isCompatible(rcFloat), "isCompatible(rcFloat) returned true");
    }

    @Test
    void testValueCompliance() {
        RangeConstraint rc = new RangeConstraint(Integer.class, 0, 2, false, false);
        String answer = RangeConstraint.VALID;
        String result = rc.getValueCompliance(1);
        assertEquals(answer, result, "getValueCompliance() return wrong value, ");

        Integer value = -1;
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");

        value = 0;
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");

        value = 3;
        answer = "value = '" + value + "' is out of range, must be less than '2'";
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");

        value = -1;
        answer = "value = '" + value + "' is out of range, must be greater than or equal to '0'";
        rc = new RangeConstraint(Integer.class, 0, 2, true, true);
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");

        value = 3;
        answer = "value = '" + value + "' is out of range, must be less than or equal to '2'";
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");
    }
}
