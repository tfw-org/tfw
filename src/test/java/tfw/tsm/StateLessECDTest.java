package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

final class StateLessECDTest {
    @Test
    void getStateTest() {
        RootFactory rf = new RootFactory();
        StatelessTriggerECD trigger = new StatelessTriggerECD("test");
        rf.addEventChannel(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("getStateTest", queue);
        TestTriggeredCommit commit = new TestTriggeredCommit(trigger);
        Initiator initiator = new Initiator("TestInitiator", trigger);
        root.add(commit);
        root.add(initiator);
        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertThat(commit.setException).isNotNull();
        assertThat(commit.map).isNotNull();
        assertThat(commit.map.size()).isEqualTo(0);
    }

    private static class TestTriggeredCommit extends TriggeredConverter {
        final StatelessTriggerECD trigger;
        IllegalArgumentException setException = null;
        Map<ObjectECD, Object> map = null;

        public TestTriggeredCommit(StatelessTriggerECD trigger) {
            super("Test", trigger, null, null);
            this.trigger = trigger;
        }

        @Override
        public void convert() {
            try {
                set(trigger, new Object());
            } catch (IllegalArgumentException expected) {
                this.setException = expected;
            }

            this.map = get();
        }
    }
}
