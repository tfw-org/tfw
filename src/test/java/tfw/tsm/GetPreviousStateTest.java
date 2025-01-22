package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class GetPreviousStateTest {
    StringECD channel = new StringECD("channel");

    @Test
    void isStateChangedTest() {
        final String initialState = "initialState";
        final String stateChangeOne = "StateOne";
        final String stateChangeTwo = "StateTwo";
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            @Override
            public void handle(Exception e) {
                assertThat(e).isNull();
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
        assertThat(initialState).isEqualTo(converter.value).isEqualTo(commit.value);

        Initiator initiator = new Initiator("initiator", channel);
        root.add(initiator);
        initiator.set(channel, stateChangeOne);
        queue.waitTilEmpty();
        assertThat(initialState).isEqualTo(converter.value).isEqualTo(commit.value);

        initiator.set(channel, stateChangeTwo);
        queue.waitTilEmpty();
        assertThat(stateChangeOne).isEqualTo(converter.value).isEqualTo(commit.value);
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
