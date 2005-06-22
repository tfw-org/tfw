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
package tfw.tsm.test;

import tfw.tsm.OneDeepStateQueueFactory;
import tfw.tsm.StateQueue;
import tfw.tsm.StateQueueFactory;

import junit.framework.TestCase;

import java.util.NoSuchElementException;


/**
 *
 */
public class OneDeepStateQueueFactoryTest extends TestCase
{
    public void testFactory()
    {
        StateQueueFactory factory = new OneDeepStateQueueFactory();
        StateQueue queue = factory.create();
        assertNotNull("factory return null", queue);
		assertTrue("isEmpty() == false when empty", queue.isEmpty());
        try
        {
            queue.pop();
            fail("pop() on an empty queue didn't throw exception!");
        }
        catch (NoSuchElementException expected)
        {
            //System.out.println(expected);
        }

        Object state = new Object();
        queue.push(state);
		assertFalse("isEmpty() == true when empty", queue.isEmpty());
		assertEquals("push/pop returned the wrong value!", state, queue.pop());
		assertTrue("isEmpty() == false after pop()", queue.isEmpty());
    }
}
