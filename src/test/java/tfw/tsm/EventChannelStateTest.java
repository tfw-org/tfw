package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class EventChannelStateTest {
    @Test
    void testEventChannelState() throws Exception {
        StringECD stringECD = new StringECD("myChannel");
        String value = "Hello World";

        try {
            new EventChannelState(null, value);
            fail("constructor accepted null ecd");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        EventChannelState state = new EventChannelState(stringECD, value);
        assertEquals(stringECD.getEventChannelName(), state.getEventChannelName(), "returned wrong ecd");
        assertEquals(value, state.getState(), "returned wrong value");
    }

    @Test
    void testEquals() throws Exception {
        StringECD stringECD = new StringECD("myChannel");
        String value = "Hello World";

        EventChannelState state1 = new EventChannelState(stringECD, value);
        EventChannelState state2 = new EventChannelState(stringECD, value);
        assertEquals(state1, state2, "equivalent values not equal");
        assertEquals(state1.hashCode(), state2.hashCode(), "equivalent values different hashCodes");
        assertNotEquals(state1, null, "null is equal");
        assertNotEquals(state1, new Object(), "wrong type is equal");

        state2 = new EventChannelState(new StringECD("different"), value);
        assertNotEquals(state1, state2, "different ecd is equal");

        state2 = new EventChannelState(stringECD, "different");
        assertNotEquals(state1, state2, "different ecd is equal");
    }
}
