package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class TreeStateBufferTest {
    @Test
    void testBuffer() throws Exception {
        TreeStateBuffer tsb = new TreeStateBuffer();
        StringECD strECD = new StringECD("mystring");

        try {
            tsb.toTreeState();
            fail("toTreeState() returned without a name");
        } catch (IllegalStateException expected) {
            // System.out.println(expected);
        }
        try {
            tsb.setName(null);
            fail("setName() accepted null value");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            tsb.addState(null);
            fail("addState() accepted null event channel state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            tsb.addChild(null);
            fail("addChild() accepted null child");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        String name = "TestTreeState";
        tsb.setName(name);

        TreeState tstate = tsb.toTreeState();
        assertNotNull(tstate, "toTreeState() returned null");
        assertEquals(name, tstate.getName(), "getName() returned wrong value");

        TreeState[] children = tstate.getChildren();
        assertNotNull(children, "getChildState() returned null");
        assertEquals(0, children.length, "getChildState() returned wrong number of children");

        EventChannelState[] ecds = tstate.getState();
        assertNotNull(ecds, "getEventChannels() returned null");
        assertEquals(0, ecds.length, "getEventChannels() returned wrong number of event channels");

        EventChannelState state = new EventChannelState(strECD, "Hello World");
        tsb.addState(state);
        tstate = tsb.toTreeState();
        ecds = tstate.getState();
        assertEquals(1, ecds.length, "getEventChannels() returned wrong number of event channels");
        assertEquals(state, tstate.getState()[0], "getState() returned the wrong value");

        tsb.setName("Parent");
        tsb.addChild(tstate);

        TreeState parent = tsb.toTreeState();
        children = parent.getChildren();
        assertEquals(1, children.length, "getChildState() return the wrong number of children");
    }
}
