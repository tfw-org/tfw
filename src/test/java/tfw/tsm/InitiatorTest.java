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

import java.util.Map;
import junit.framework.TestCase;
import tfw.tsm.AlwaysChangeRule;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

/**
 * 
 */
public class InitiatorTest extends TestCase
{
    ObjectECD channel1 = new StringECD("aaa");

    ObjectECD channel2 = new StringECD("bbb");

    ObjectECD[] channels = new ObjectECD[] { channel1, channel2 };

    public void testInitiatorConstruction()
    {
        try
        {
            new Initiator(null, channel1);
            fail("Constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            new Initiator("test", (ObjectECD) null);
            fail("Constructor accepted null source");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            new Initiator("test", (ObjectECD[]) null);
            fail("Constructor accepted null sources");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
    }

    public void testInitatorSetMehtods()
    {
        Initiator initiator = new Initiator("test", channels);

        try
        {
            initiator.set((ObjectECD) null, new Object());
            fail("set(channel, state) accepted null channel");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            initiator.set((StringECD) null, new Object());
            fail("set(eventChannelName, state) accepted null eventChannelName");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            initiator.set(channel1, null);
            fail("set(channel, state) accepted null state");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        try
        {
            initiator.set(null);
            fail("set(state) accepted null state");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
        {
            public void handle(Exception exception)
            {
                exception.printStackTrace();
                fail("Exception: " + exception.getMessage());
            }
        });
        rf.addEventChannel(channel1, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(channel2, null, AlwaysChangeRule.RULE, null);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("test", queue);
        root.add(initiator);

        TestCommit commit = new TestCommit("testcommit", channels);
        root.add(commit);

        String state1 = "Hello";
        String state2 = "World";

        initiator.set(channel1, state1);
        queue.waitTilEmpty();
        assertNotNull("debugCommit() was not called", commit.debugCommitState);
        assertEquals("debugCommit() received wrong state", state1,
                commit.debugCommitState.get(channel1));
        commit.debugCommitState = null;
        initiator.set(channel2, state2);
        queue.waitTilEmpty();
        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);

        // reset all of the infrastructure...
        root.remove(initiator);
        root.remove(commit);
        commit.commitState = null;
        commit.debugCommitState = null;
        // rf = new RootFactory();
        // rf.addTerminator(channel1);
        // rf.addTerminator(channel2);
        root.add(initiator);
        root.add(commit);

        EventChannelStateBuffer stateBuff = new EventChannelStateBuffer();
        stateBuff.put(channel1, state1);
        stateBuff.put(channel2, state2);
        queue.waitTilEmpty();
        initiator.set(stateBuff.toArray());
        queue.waitTilEmpty();
        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);

        // Test set(Map) before connection...
        commit.commitState = null;
        commit.debugCommitState = null;
        root.remove(initiator);
//        initiator = new Initiator("test", channels);
        initiator.set(stateBuff.toArray());
        queue.waitTilEmpty();
        root.add(initiator);
        queue.waitTilEmpty();
        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);

        // Test set(EventChannelName, State) before connection...
        // This tests the delay fire requirement for an intiator.
        commit.commitState = null;
        commit.debugCommitState = null;
        root.remove(initiator);
        queue.waitTilEmpty();
//        initiator = new Initiator("test", channels);
        initiator.set(channel1, state1);
        initiator.set(channel2, state2);

        root.add(initiator);
        queue.waitTilEmpty();

        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);

        root.remove(initiator);
        queue.waitTilEmpty();

        commit.commitState = null;
        commit.debugCommitState = null;
        root.add(initiator);
        initiator.set(channel1, state1);
        initiator.set(channel2, state2);
        root.remove(initiator);
        queue.waitTilEmpty();
        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);
        rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
        {
            public void handle(Exception exception)
            {
                exception.printStackTrace();
                fail("Exception: " + exception.getMessage());
            }
        });
        rf.addEventChannel(channel1, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(channel2, null, AlwaysChangeRule.RULE, null);

        Root root2 = rf.create("Root2", queue);
        initiator.set(channel1, state1);
        initiator.set(channel2, state2);

        commit.commitState = null;
        commit.debugCommitState = null;
        root.add(initiator);
        root.remove(initiator);
        root2.add(initiator);
        queue.waitTilEmpty();
        
        assertNotNull("commit() not called", commit.commitState);
        assertEquals("commit() received wrong state1", state1,
                commit.commitState.get(channel1));
        assertEquals("commit() received wrong state2", state2,
                commit.commitState.get(channel2));

        assertNull("debugCommit() was called", commit.debugCommitState);
    }

    private class TestCommit extends Commit
    {
        public Map<ObjectECD, Object> commitState = null;
        public Map<ObjectECD, Object> debugCommitState = null;

        public TestCommit(String name, ObjectECD[] ecds)
        {
            super(name, ecds);
        }

        public void commit()
        {
            commitState = this.get();

            // System.out.println("commit() - " + commitState);
        }

        public void debugCommit()
        {
            debugCommitState = this.get();

            // System.out.println("debugCommit() - " + debugCommitState);
        }
    }
}
