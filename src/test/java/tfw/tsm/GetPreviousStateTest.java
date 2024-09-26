package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

class GetPreviousStateTest {
    StringECD channel = new StringECD("channel");

    @Test
    void testIsStateChanged() {
        final String initialState = "initialState";
        final String stateChangeOne = "StateOne";
        final String stateChangeTwo = "StateTwo";
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            @Override
            public void handle(Exception e) {
                e.printStackTrace();
                fail("Test failed with an exception: " + e.getMessage());
            }
        });
        rf.addEventChannel(channel, initialState);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("TestRoot", queue);

        TestConverter converter = new TestConverter();
        TestCommit commit = new TestCommit();
        root.add(converter);
        root.add(commit);
        queue.waitTilEmpty();
        assertEquals(initialState, converter.value, "getPreviousCycleState() failed inital value = " + converter.value);
        assertEquals(
                initialState, commit.value, "getPreviousTransactionState() failed initial value = " + commit.value);

        Initiator initiator = new Initiator("initiator", channel);
        root.add(initiator);
        initiator.set(channel, stateChangeOne);
        queue.waitTilEmpty();
        assertEquals(initialState, converter.value, "getPreviousCycleState() failed inital value = " + converter.value);
        assertEquals(
                initialState, commit.value, "getPreviousTransactionState() failed initial value = " + commit.value);

        initiator.set(channel, stateChangeTwo);
        queue.waitTilEmpty();
        assertEquals(
                stateChangeOne, converter.value, "getPreviousCycleState() failed inital value = " + converter.value);
        assertEquals(
                stateChangeOne, commit.value, "getPreviousTransactionState() failed initial value = " + commit.value);
    }

    private class TestConverter extends Converter {
        private String value = null;

        public TestConverter() {
            super("TestConverter", new StringECD[] {channel}, new StringECD[] {});
        }

        @Override
        protected void convert() {
            value = (String) this.getPreviousCycleState(channel);
        }
    }

    private class TestCommit extends Commit {
        private String value = null;

        public TestCommit() {
            super("TestCommit", new StringECD[] {channel});
        }

        @Override
        protected void commit() {
            value = (String) this.getPreviousTransactionState(channel);
        }
    }
}
