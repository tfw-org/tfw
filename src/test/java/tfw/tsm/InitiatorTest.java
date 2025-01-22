package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class InitiatorTest {
    ObjectECD channel1 = new StringECD("aaa");
    ObjectECD channel2 = new StringECD("bbb");
    ObjectECD[] channels = new ObjectECD[] {channel1, channel2};

    @Test
    void initiatorConstructionTest() {
        assertThatThrownBy(() -> new Initiator(null, channel1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> new Initiator("test", (ObjectECD) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0]== null not allowed!");
        assertThatThrownBy(() -> new Initiator("test", (ObjectECD[]) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources == null not allowed!");
    }

    @Test
    void initatorSetMehtodsTest() {
        Initiator initiator = new Initiator("test", channels);
        Object object = new Object();

        assertThatThrownBy(() -> initiator.set((ObjectECD) null, object))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sourceEventChannel == null not allowed!");

        assertThatThrownBy(() -> initiator.set((StringECD) null, object))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sourceEventChannel == null not allowed!");

        assertThatThrownBy(() -> initiator.set(channel1, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value == null does not meet the constraints on this value");

        assertThatThrownBy(() -> initiator.set(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("state == null not allowed!");

        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            @Override
            public void handle(Exception exception) {
                assertThat(exception).isNull();
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
        assertThat(commit.debugCommitState).isNotNull();
        assertThat(state1).isEqualTo(commit.debugCommitState.get(channel1));
        commit.debugCommitState = null;
        initiator.set(channel2, state2);
        queue.waitTilEmpty();
        assertThat(commit.commitState).isNotNull();
        assertThat(state1).isEqualTo(commit.commitState.get(channel1));
        assertThat(state2).isEqualTo(commit.commitState.get(channel2));

        assertThat(commit.debugCommitState).isNull();

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
        assertThat(commit.commitState).isNotNull();
        assertThat(state1).isEqualTo(commit.commitState.get(channel1));
        assertThat(state2).isEqualTo(commit.commitState.get(channel2));

        assertThat(commit.debugCommitState).isNull();
        ;

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

        assertThat(commit.debugCommitState).isNull();

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
        assertThat(commit.commitState).isNotNull();
        assertThat(state1).isEqualTo(commit.commitState.get(channel1));
        assertThat(state2).isEqualTo(commit.commitState.get(channel2));

        assertThat(commit.debugCommitState).isNull();
        rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            @Override
            public void handle(Exception exception) {
                assertThat(exception).isNull();
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

    private static class TestCommit extends Commit {
        public Map<ObjectECD, Object> commitState = null;
        public Map<ObjectECD, Object> debugCommitState = null;

        public TestCommit(String name, ObjectECD[] ecds) {
            super(name, ecds);
        }

        @Override
        public void commit() {
            commitState = this.get();
        }

        @Override
        public void debugCommit() {
            debugCommitState = this.get();
        }
    }
}
