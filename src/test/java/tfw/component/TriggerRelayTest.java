package tfw.component;

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TriggerRelayTest extends TestCase {
    private final StatelessTriggerECD triggerToRelayECD = new StatelessTriggerECD("triggerToRelay");

    private final StatelessTriggerECD relayedTriggerECD = new StatelessTriggerECD("relayedTrigger");

    public void testTriggerRelay() {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerToRelayECD);
        rf.addEventChannel(relayedTriggerECD);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Root", queue);
        Initiator initiator = new Initiator("initiator", triggerToRelayECD);
        MyCommit commit = new MyCommit();
        root.add(initiator);
        root.add(commit);
        root.add(new TriggerRelay("relay", triggerToRelayECD, relayedTriggerECD));
        initiator.trigger(triggerToRelayECD);
        queue.waitTilEmpty();
        assertTrue("Trigger not relayed!", commit.executed);
    }

    private class MyCommit extends TriggeredCommit {
        public boolean executed = false;

        public MyCommit() {
            super("MyCommit", relayedTriggerECD);
        }

        protected void commit() {
            this.executed = true;
        }
    }
}
