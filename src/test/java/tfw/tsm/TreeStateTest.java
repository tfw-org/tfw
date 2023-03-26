package tfw.tsm;

import tfw.tsm.EventChannelState;
import tfw.tsm.TreeState;
import tfw.tsm.ecd.StringECD;

import junit.framework.TestCase;


/**
 *
 */
public class TreeStateTest extends TestCase
{
    public void testEquals() throws Exception
    {
        String name = "my tree";
        EventChannelState state = new EventChannelState(new StringECD("xyz"),
                "Hello");
        EventChannelState[] stateArray = new EventChannelState[]{  };
        TreeState child = new TreeState("child", null, null);
        TreeState[] children = new TreeState[]{ child };
        TreeState ts1 = new TreeState(name, stateArray, children);
        TreeState ts2 = new TreeState(name, stateArray, children);
        assertEquals("Equivalent tree state not equal", ts1, ts2);
        assertEquals("Equivalent tree state has different hash code",
            ts1.hashCode(), ts2.hashCode());

        assertFalse("null is equal", ts1.equals(null));
        assertFalse("wrong type is equal", ts1.equals(new Object()));

        ts2 = new TreeState("different", stateArray, children);
        assertFalse("different name equal", ts1.equals(ts2));

        ts2 = new TreeState(name, new EventChannelState[]{ state }, children);
        assertFalse("different event channel state equal", ts1.equals(ts2));

        ts2 = new TreeState(name, stateArray, new TreeState[0]);
        assertFalse("different children equal", ts1.equals(ts2));
    }
}
