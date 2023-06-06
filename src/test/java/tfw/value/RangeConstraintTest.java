package tfw.value;

import junit.framework.TestCase;

/**
 *
 */
public class RangeConstraintTest extends TestCase {
    public void testConstructionNullMinimum() {
        try {
            new RangeConstraint(Integer.class, null, new Integer(10), true, true);
            fail("Constructor accepted null minimum!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    public void testConstructionNullMaximum() {
        try {
            new RangeConstraint(Integer.class, new Integer(0), null, true, true);
            fail("Constructor accepted null maximum!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    public void testConstructorMinGreaterThanMax() {
        try {
            new RangeConstraint(Integer.class, new Integer(10), new Integer(0), true, true);
            fail("Constructor accepted min > max!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    public void testConstructorMinEqualMaxNeitherInclusive() {
        try {
            new RangeConstraint(Integer.class, new Integer(0), new Integer(0), false, false);
            fail("Constructor accepted min == max neither inclusive!");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    public void testIsCompatibles() {
        RangeConstraint rc = new RangeConstraint(Integer.class, new Integer(0), new Integer(2), false, false);
        assertFalse("isCompatible(null) returned true", rc.isCompatible(null));

        RangeConstraint rcLow = new RangeConstraint(Integer.class, new Integer(0), new Integer(0), true, true);
        assertFalse("isCompatible(rcLow) returned true", rc.isCompatible(rcLow));

        RangeConstraint rcHigh = new RangeConstraint(Integer.class, new Integer(2), new Integer(2), true, true);
        assertFalse("isCompatible(rcHigh) returned true", rc.isCompatible(rcHigh));

        RangeConstraint rcMiddle = new RangeConstraint(Integer.class, new Integer(1), new Integer(1), true, true);
        assertTrue("isCompatible(rcMiddle) returned false", rc.isCompatible(rcMiddle));

        assertTrue("isCompatible(rc) returned false", rc.isCompatible(rc));

        RangeConstraint rcFloat = new RangeConstraint(Float.class, new Float(1.0), new Float(1.0), true, true);

        assertFalse("isCompatible(rcFloat) returned true", rc.isCompatible(rcFloat));
    }

    public void testValueCompliance() {
        RangeConstraint rc = new RangeConstraint(Integer.class, new Integer(0), new Integer(2), false, false);
        String answer = RangeConstraint.VALID;
        String result = rc.getValueCompliance(new Integer(1));
        assertEquals("getValueCompliance() return wrong value, ", answer, result);

        Integer value = new Integer(-1);
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertEquals("getValueCompliance() returned wrong value, ", answer, result);

        value = new Integer(0);
        answer = "value = '" + value + "' is out of range, must be greater than '0'";
        result = rc.getValueCompliance(value);
        assertEquals("getValueCompliance() returned wrong value, ", answer, result);

        value = new Integer(3);
        answer = "value = '" + value + "' is out of range, must be less than '2'";
        result = rc.getValueCompliance(value);
        assertEquals("getValueCompliance() returned wrong value, ", answer, result);

        value = new Integer(-1);
        answer = "value = '" + value + "' is out of range, must be greater than or equal to '0'";
        rc = new RangeConstraint(Integer.class, new Integer(0), new Integer(2), true, true);
        result = rc.getValueCompliance(value);
        assertEquals("getValueCompliance() returned wrong value, ", answer, result);

        value = new Integer(3);
        answer = "value = '" + value + "' is out of range, must be less than or equal to '2'";
        result = rc.getValueCompliance(value);
        assertEquals("getValueCompliance() returned wrong value, ", answer, result);
    }
}
