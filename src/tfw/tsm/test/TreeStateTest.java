/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm.test;

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
