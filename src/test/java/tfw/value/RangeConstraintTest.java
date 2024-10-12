package tfw.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 */
class RangeConstraintTest {
    @Test
    void testConstructionNullMinimum() {
        final Integer ten = Integer.valueOf(10);

        assertThrows(
                IllegalArgumentException.class,
                () -> new TestIntegerRangeConstraint(Integer.class, null, ten, true, true));
    }

    @Test
    void testConstructionNullMaximum() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new TestIntegerRangeConstraint(Integer.class, 0, null, true, true));
    }

    @Test
    void testConstructorMinGreaterThanMax() {
        assertThrows(
                IllegalArgumentException.class, () -> new TestIntegerRangeConstraint(Integer.class, 10, 0, true, true));
    }

    @Test
    void testConstructorMinEqualMaxNeitherInclusive() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new TestIntegerRangeConstraint(Integer.class, 0, 0, false, false));
    }

    @Test
    void testIsCompatibles() {
        RangeConstraint<Integer> rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, false, false);
        assertFalse(rc.isCompatible(null), "isCompatible(null) returned true");

        RangeConstraint<Integer> rcLow = new TestIntegerRangeConstraint(Integer.class, 0, 0, true, true);
        assertFalse(rc.isCompatible(rcLow), "isCompatible(rcLow) returned true");

        RangeConstraint<Integer> rcHigh = new TestIntegerRangeConstraint(Integer.class, 2, 2, true, true);
        assertFalse(rc.isCompatible(rcHigh), "isCompatible(rcHigh) returned true");

        RangeConstraint<Integer> rcMiddle = new TestIntegerRangeConstraint(Integer.class, 1, 1, true, true);
        assertTrue(rc.isCompatible(rcMiddle), "isCompatible(rcMiddle) returned false");

        assertTrue(rc.isCompatible(rc), "isCompatible(rc) returned false");
    }

    @Test
    void testValueCompliance() {
        RangeConstraint<Integer> rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, false, false);
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
        rc = new TestIntegerRangeConstraint(Integer.class, 0, 2, true, true);
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");

        value = 3;
        answer = "value = '" + value + "' is out of range, must be less than or equal to '2'";
        result = rc.getValueCompliance(value);
        assertEquals(answer, result, "getValueCompliance() returned wrong value, ");
    }

    private static class TestIntegerRangeConstraint extends RangeConstraint<Integer> {
        public TestIntegerRangeConstraint(
                Class<Integer> valueType, Integer min, Integer max, boolean minInclusive, boolean maxInclusive) {
            super(valueType, min, max, minInclusive, maxInclusive);
        }

        @Override
        protected int compareTo(Integer left, Integer right) {
            return left.compareTo(right);
        }
    }
}
