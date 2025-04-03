package tfw.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.StatelessTriggerECD;

final class TriggerRelayTest {
    private final StatelessTriggerECD triggerToRelayECD = new StatelessTriggerECD("triggerToRelay");

    private final StatelessTriggerECD relayedTriggerECD = new StatelessTriggerECD("relayedTrigger");

    @Test
    void triggerRelayTest() {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerToRelayECD);
        rf.addEventChannel(relayedTriggerECD);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Root", queue);
        Initiator initiator = new Initiator("initiator", triggerToRelayECD);
        CommitImpl commit = new CommitImpl();
        root.add(initiator);
        root.add(commit);
        root.add(new TriggerRelay("relay", triggerToRelayECD, relayedTriggerECD));
        initiator.trigger(triggerToRelayECD);
        queue.waitTilEmpty();
        assertThat(commit.executed).isTrue();
    }

    private class CommitImpl extends TriggeredCommit {
        public boolean executed = false;

        public CommitImpl() {
            super("CommitImpl", relayedTriggerECD);
        }

        @Override
        protected void commit() {
            this.executed = true;
        }
    }
}
