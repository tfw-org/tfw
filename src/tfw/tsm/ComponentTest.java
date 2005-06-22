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
 * witout even the implied warranty of
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


/**
 *
 */
class ComponentTest extends TestCase
{
    public void testConstructor()
    {
        try
        {
            new TreeComponent(null, null, null, null);
            fail("Constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    //
    // Test add(Component)
    //
    public void testAddNullComponent()
    {
        TreeComponent parent = new TreeComponent("parent", null, null, null);

        try
        {
            parent.add((TreeComponent) null);
            fail("add() accepted null child");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            parent.remove((TreeComponent) null);
            fail("remove() accepted null child");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            parent.add(parent);
            fail("add() accepted self as child");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
//
//        Component child = new Component("child", new HashMap(),
//                new HashSet());
//        parent.add(child);
//        parent.remove(child);
//
//        try
//        {
//            parent.add(child);
//        }
//        catch (IllegalArgumentException unexpected)
//        {
//            fail("add() remove() add() threw an exception");
//        }
//
//        try
//        {
//            parent.add(child);
//            fail("add() accepted same child twice");
//        }
//        catch (IllegalArgumentException expected)
//        {
//            //System.out.println(expected);
//        }
    }

//    public void testIsRooted()
//    {
//        Component root = new Component("Root", new HashMap(),
//                new HashSet())
//            {
//                public boolean isRooted()
//                {
//                    return true;
//                }
//            };
//
//        Component child = new Component("child", new HashMap(),
//                new HashSet());
//
//        try
//        {
//            child.add(root);
//            fail("add() accepted rooted child");
//        }
//        catch (IllegalArgumentException expected)
//        {
//            //System.out.println(expected);
//        }
//    }
//
//    public void testGetName()
//    {
//        String name = "myname";
//        assertEquals("getName() return wrong value,", name,
//            (new Component(name, new HashMap(), new HashSet()).getName()));
//    }

//    public void testLocalTermination()
//    {
//        String eventChannelName = "testEventChannel";
//        ValueConstraint constraint = new ValueConstraint(String.class);
//        Terminator terminator = new Terminator(eventChannelName, new Object(),
//                null, true, true);
//        HashMap terminators = new HashMap();
//        terminators.put(eventChannelName, terminator);
//
//        Root root = new Root("testRoot", terminators, new HashSet());
//
//        Sink sink = new Sink("testSink", eventChannelName, constraint){
//        	public void stateChange(){}
//        };
//        
//    }
}
