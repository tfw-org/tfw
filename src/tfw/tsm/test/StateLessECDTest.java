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

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.StateMap;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.StatelessTriggerECD;

import junit.framework.TestCase;


/**
 *
 */
public class StateLessECDTest extends TestCase
{
    public void testGetState()
    {
        RootFactory rf = new RootFactory();
        StatelessTriggerECD trigger = new StatelessTriggerECD("test");
        rf.addTerminator(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("getStateTest", queue);
        MyTriggeredCommit commit = new MyTriggeredCommit(trigger);
        Initiator initiator = new Initiator("TestInitiator", trigger);
        root.add(commit);
        root.add(initiator);
        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertNotNull("get(ECD) of a statelessECD didn't throw an exception",
            commit.getException);
        assertNotNull("set() of a statelessECD didn't throw an exception",
            commit.setException);
        assertNotNull("get() returned null state map", commit.map);
        assertEquals("get() returned the wron number of values", 0,
            commit.map.size());
        //System.out.println(commit.setException);
    }

    private class MyTriggeredCommit extends TriggeredConverter
    {
        final StatelessTriggerECD trigger;
        IllegalArgumentException getException = null;
        IllegalArgumentException setException = null;
        StateMap map = null;

        public MyTriggeredCommit(StatelessTriggerECD trigger)
        {
            super("Test", trigger, null, null);
            this.trigger = trigger;
        }

        public void convert()
        {
            try
            {
                get(trigger);
            }
            catch (IllegalArgumentException expected)
            {
                this.getException = expected;
            }

            try
            {
                set(trigger, new Object());
            }
            catch (IllegalArgumentException expected)
            {
                this.setException = expected;
            }

            this.map = get();
        }
    }
}
