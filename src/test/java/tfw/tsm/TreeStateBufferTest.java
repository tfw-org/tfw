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
package tfw.tsm;

import junit.framework.TestCase;
import tfw.tsm.EventChannelState;
import tfw.tsm.TreeState;
import tfw.tsm.TreeStateBuffer;
import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class TreeStateBufferTest extends TestCase
{
    public void testBuffer() throws Exception
    {
        TreeStateBuffer tsb = new TreeStateBuffer();
        StringECD strECD = new StringECD("mystring");

		try{
			tsb.toTreeState();
			fail("toTreeState() returned without a name");
		} catch (IllegalStateException expected){
			//System.out.println(expected);
		}
        try
        {
            tsb.setName(null);
            fail("setName() accepted null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            tsb.addState(null);
            fail("addState() accepted null event channel state");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            tsb.addChild(null);
            fail("addChild() accepted null child");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        String name = "TestTreeState";
        tsb.setName(name);

        TreeState tstate = tsb.toTreeState();
        assertNotNull("toTreeState() returned null", tstate);
        assertEquals("getName() returned wrong value", name, tstate.getName());

        TreeState[] children = tstate.getChildren();
        assertNotNull("getChildState() returned null", children);
        assertEquals("getChildState() returned wrong number of children", 0,
            children.length);

        EventChannelState[] ecds = tstate.getState();
        assertNotNull("getEventChannels() returned null", ecds);
        assertEquals("getEventChannels() returned wrong number of event channels",
            0, ecds.length);

        EventChannelState state = new EventChannelState(strECD, "Hello World");
        tsb.addState(state);
        tstate = tsb.toTreeState();
        ecds = tstate.getState();
        assertEquals("getEventChannels() returned wrong number of event channels",
            1, ecds.length);
        assertEquals("getState() returned the wrong value", state,
            tstate.getState()[0]);

        tsb.setName("Parent");
        tsb.addChild(tstate);

        TreeState parent = tsb.toTreeState();
        children = parent.getChildren();
        assertEquals("getChildState() return the wrong number of children", 1,
            children.length);
    }
}
