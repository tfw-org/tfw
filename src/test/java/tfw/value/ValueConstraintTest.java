package tfw.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 *
 */
class ValueConstraintTest {
    private final class MyConstraint extends ClassValueConstraint {
        public MyConstraint(Class valueType) {
            super(valueType);
        }
    }

    @Test
    void testNullValueType() {
        try {
            new MyConstraint(null);
            fail("Constructor accepted null valueConstraint");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testGetValueCompliance() {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);
        String result = vc.getValueCompliance(0);
        assertEquals(ClassValueConstraint.VALID, result, "Valid class type rejected!");
        result = vc.getValueCompliance(null);
        assertNotEquals(ClassValueConstraint.VALID, result, "null accepted!");
        result = vc.getValueCompliance(new Object());
        assertNotEquals(ClassValueConstraint.VALID, result, "accepted invalid type!");
    }

    @Test
    void testIsValid() {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);
        assertTrue(vc.isValid(1), "rejected valid value!");
        assertFalse(vc.isValid(new Object()), "accepted an invalid value!");
    }

    @Test
    void testCheckValue() {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);

        try {
            vc.checkValue(new Object());
            fail("checkValue() accepted invalid value");
        } catch (ValueException expected) {
            // System.out.println(expected);
        }

        try {
            vc.checkValue(0);
        } catch (ValueException unexpected) {
            fail("checkValue() threw exception on valid value");
        }
    }
}
