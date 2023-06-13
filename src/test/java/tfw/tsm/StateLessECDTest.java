package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

/**
 *
 */
class StateLessECDTest {
    @Test
    void testGetState() {
        RootFactory rf = new RootFactory();
        StatelessTriggerECD trigger = new StatelessTriggerECD("test");
        rf.addEventChannel(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("getStateTest", queue);
        MyTriggeredCommit commit = new MyTriggeredCommit(trigger);
        Initiator initiator = new Initiator("TestInitiator", trigger);
        root.add(commit);
        root.add(initiator);
        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertNotNull(commit.setException, "set() of a statelessECD didn't throw an exception");
        assertNotNull(commit.map, "get() returned null state map");
        assertEquals(0, commit.map.size(), "get() returned the wron number of values");
        // System.out.println(commit.setException);
    }

    private class MyTriggeredCommit extends TriggeredConverter {
        final StatelessTriggerECD trigger;
        IllegalArgumentException getException = null;
        IllegalArgumentException setException = null;
        Map<ObjectECD, Object> map = null;

        public MyTriggeredCommit(StatelessTriggerECD trigger) {
            super("Test", trigger, null, null);
            this.trigger = trigger;
        }

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
