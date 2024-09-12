package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class InitiatorTest {
    ObjectECD channel1 = new StringECD("aaa");

    ObjectECD channel2 = new StringECD("bbb");

    ObjectECD[] channels = new ObjectECD[] {channel1, channel2};

    @Test
    void testInitiatorConstruction() {
        try {
            new Initiator(null, channel1);
            fail("Constructor accepted null name");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new Initiator("test", (ObjectECD) null);
            fail("Constructor accepted null source");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            new Initiator("test", (ObjectECD[]) null);
            fail("Constructor accepted null sources");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    @Test
    void testInitatorSetMehtods() {
        Initiator initiator = new Initiator("test", channels);

        try {
            initiator.set((ObjectECD) null, new Object());
            fail("set(channel, state) accepted null channel");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            initiator.set((StringECD) null, new Object());
            fail("set(eventChannelName, state) accepted null eventChannelName");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            initiator.set(channel1, null);
            fail("set(channel, state) accepted null state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        try {
            initiator.set(null);
            fail("set(state) accepted null state");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }

        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            public void handle(Exception exception) {
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
        assertNotNull(commit.debugCommitState, "debugCommit() was not called");
        assertEquals(state1, commit.debugCommitState.get(channel1), "debugCommit() received wrong state");
        commit.debugCommitState = null;
        initiator.set(channel2, state2);
        queue.waitTilEmpty();
        assertNotNull(commit.commitState, "commit() not called");
        assertEquals(state1, commit.commitState.get(channel1), "commit() received wrong state1");
        assertEquals(state2, commit.commitState.get(channel2), "commit() received wrong state2");

        assertNull(commit.debugCommitState, "debugCommit() was called");

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
        assertNotNull(commit.commitState, "commit() not called");
        assertEquals(state1, commit.commitState.get(channel1), "commit() received wrong state1");
        assertEquals(state2, commit.commitState.get(channel2), "commit() received wrong state2");

        assertNull(commit.debugCommitState, "debugCommit() was called");

        // Test set(Map) before connection...
        //        commit.commitState = null;
        //        commit.debugCommitState = null;
        //        root.remove(initiator);
        ////        initiator = new Initiator("test", channels);
        //        initiator.set(stateBuff.toArray());
        //        queue.waitTilEmpty();
        //        root.add(initiator);
        //        queue.waitTilEmpty();
        //        assertNotNull("commit() not called", commit.commitState);
        //        assertEquals("commit() received wrong state1", state1,
        //                commit.commitState.get(channel1));
        //        assertEquals("commit() received wrong state2", state2,
        //                commit.commitState.get(channel2));

        assertNull(commit.debugCommitState, "debugCommit() was called");

        // Test set(EventChannelName, State) before connection...
        // This tests the delay fire requirement for an intiator.
        //        commit.commitState = null;
        //        commit.debugCommitState = null;
        //        root.remove(initiator);
        //        queue.waitTilEmpty();
        ////        initiator = new Initiator("test", channels);
        //        initiator.set(channel1, state1);
        //        initiator.set(channel2, state2);
        //
        //        root.add(initiator);
        //        queue.waitTilEmpty();
        //
        //        assertNotNull("commit() not called", commit.commitState);
        //        assertEquals("commit() received wrong state1", state1,
        //                commit.commitState.get(channel1));
        //        assertEquals("commit() received wrong state2", state2,
        //                commit.commitState.get(channel2));
        //
        //        assertNull("debugCommit() was called", commit.debugCommitState);

        root.remove(initiator);
        queue.waitTilEmpty();

        commit.commitState = null;
        commit.debugCommitState = null;
        root.add(initiator);
        initiator.set(channel1, state1);
        initiator.set(channel2, state2);
        root.remove(initiator);
        queue.waitTilEmpty();
        assertNotNull(commit.commitState, "commit() not called");
        assertEquals(state1, commit.commitState.get(channel1), "commit() received wrong state1");
        assertEquals(state2, commit.commitState.get(channel2), "commit() received wrong state2");

        assertNull(commit.debugCommitState, "debugCommit() was called");
        rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            public void handle(Exception exception) {
                exception.printStackTrace();
                fail("Exception: " + exception.getMessage());
            }
        });
        rf.addEventChannel(channel1, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(channel2, null, AlwaysChangeRule.RULE, null);

        //        Root root2 = rf.create("Root2", queue);
        //        initiator.set(channel1, state1);
        //        initiator.set(channel2, state2);
        //
        //        commit.commitState = null;
        //        commit.debugCommitState = null;
        //        root.add(initiator);
        //        root.remove(initiator);
        //        root2.add(initiator);
        //        queue.waitTilEmpty();
        //
        //        assertNotNull("commit() not called", commit.commitState);
        //        assertEquals("commit() received wrong state1", state1,
        //                commit.commitState.get(channel1));
        //        assertEquals("commit() received wrong state2", state2,
        //                commit.commitState.get(channel2));
        //
        //        assertNull("debugCommit() was called", commit.debugCommitState);
    }

    private class TestCommit extends Commit {
        public Map<ObjectECD, Object> commitState = null;
        public Map<ObjectECD, Object> debugCommitState = null;

        public TestCommit(String name, ObjectECD[] ecds) {
            super(name, ecds);
        }

        public void commit() {
            commitState = this.get();

            // System.out.println("commit() - " + commitState);
        }

        public void debugCommit() {
            debugCommitState = this.get();

            // System.out.println("debugCommit() - " + debugCommitState);
        }
    }
}
