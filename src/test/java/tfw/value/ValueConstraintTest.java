package tfw.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 *
 */
class ValueConstraintTest {
    private static final class TestConstraint extends ClassValueConstraint<Object> {
        public TestConstraint(Class<Object> valueType) {
            super(valueType);
        }
    }

    @Test
    void testNullValueType() {
        assertThrows(IllegalArgumentException.class, () -> new TestConstraint(null));
    }

    @Test
    void testGetValueCompliance() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);
        String result = vc.getValueCompliance(0);
        assertEquals(ClassValueConstraint.VALID, result, "Valid class type rejected!");
        result = vc.getValueCompliance(null);
        assertNotEquals(ClassValueConstraint.VALID, result, "null accepted!");
        result = vc.getValueCompliance(new Object());
        assertNotEquals(ClassValueConstraint.VALID, result, "accepted invalid type!");
    }

    @Test
    void testIsValid() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);
        assertTrue(vc.isValid(1), "rejected valid value!");
    }

    @Test
    void testCheckValue() {
        ClassValueConstraint<Integer> vc = ClassValueConstraint.getInstance(Integer.class);

        final Object v = new Object();

        assertThrows(ValueException.class, () -> vc.checkValue(v));

        try {
            vc.checkValue(0);
        } catch (ValueException unexpected) {
            fail("checkValue() threw exception on valid value");
        }
    }
}
