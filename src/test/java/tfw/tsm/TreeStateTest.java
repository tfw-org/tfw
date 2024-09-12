package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class TreeStateTest {
    @Test
    void testEquals() throws Exception {
        String name = "my tree";
        EventChannelState state = new EventChannelState(new StringECD("xyz"), "Hello");
        EventChannelState[] stateArray = new EventChannelState[] {};
        TreeState child = new TreeState("child", null, null);
        TreeState[] children = new TreeState[] {child};
        TreeState ts1 = new TreeState(name, stateArray, children);
        TreeState ts2 = new TreeState(name, stateArray, children);
        assertEquals(ts1, ts2, "Equivalent tree state not equal");
        assertEquals(ts1.hashCode(), ts2.hashCode(), "Equivalent tree state has different hash code");

        assertNotEquals(ts1, null, "null is equal");
        assertNotEquals(ts1, new Object(), "wrong type is equal");

        ts2 = new TreeState("different", stateArray, children);
        assertNotEquals(ts1, ts2, "different name equal");

        ts2 = new TreeState(name, new EventChannelState[] {state}, children);
        assertNotEquals(ts1, ts2, "different event channel state equal");

        ts2 = new TreeState(name, stateArray, new TreeState[0]);
        assertNotEquals(ts1, ts2, "different children equal");
    }
}
