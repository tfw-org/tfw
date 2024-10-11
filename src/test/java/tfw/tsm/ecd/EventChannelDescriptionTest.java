package tfw.tsm.ecd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.value.ClassValueConstraint;
import tfw.value.ValueConstraint;

/**
 *
 */
class EventChannelDescriptionTest {
    @Test
    void testConstruction() {
        try {
            new TestECD(null, ClassValueConstraint.STRING);
            fail("constructor accepted null name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestECD(" ", ClassValueConstraint.STRING);
            fail("constructor accepted empty name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestECD("A", null);
            fail("constructor accepted constraint");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new TestECD("A", ClassValueConstraint.STRING);
        } catch (IllegalArgumentException expected) {
            fail("constructor did not accept a null codec");
        }
    }

    @Test
    void testEquals() {
        TestECD ecd1 = new TestECD("A", ClassValueConstraint.STRING);
        TestECD ecd2 = new TestECD("A", ClassValueConstraint.STRING);
        assertEquals(ecd1, ecd2, "Equivalent instances not equal");
        assertNotEquals(null, ecd1, "equal to null.");

        ecd2 = new TestECD("different", ClassValueConstraint.STRING);
        assertNotEquals(ecd2, ecd1, "different names equal");

        // ecd2 = new TestECD("A", ClassValueConstraint.BOOLEAN);
        // assertFalse(ecd1.equals(ecd2), "different constraints equal");

        //		ecd2 = new TestECD("A", ClassValueConstraint.STRING);
        //		assertFalse("different codecs equal",ecd1.equals(ecd2));

        //		ecd2 = new TestECD("A", ClassValueConstraint.STRING,
        //						StringCodec.INSTANCE, true, false);
        //		assertFalse("different fire-on-connect equal",ecd1.equals(ecd2));

        //		ecd2 = new TestECD("A", ClassValueConstraint.STRING,
        //						StringCodec.INSTANCE, false, true);
        //		assertFalse("different rollback participant equal",ecd1.equals(ecd2));
    }

    private static class TestECD extends ObjectECD {
        public TestECD(String eventChannelName, ValueConstraint<String> constraint) {
            super(eventChannelName, constraint);
        }
    }
}
