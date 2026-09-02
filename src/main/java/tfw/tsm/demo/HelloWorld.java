package tfw.tsm.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.DefaultCheckDependencies;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class HelloWorld {
    public static final String HELLO_WORLD_STRING = "HelloWorld!";
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

    public static final void main(String[] args) throws InterruptedException {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        final Initiator initiator = new Initiator("Hello World Initiator", new EventChannelDescription[] {triggerECD});
        final TriggeredCommit commit = new TriggeredCommit("Hello World Commit", triggerECD, null, null) {
            @Override
            protected void commit() {
                LOG.atInfo().log(HELLO_WORLD_STRING);
            }
        };
        final Root root = Root.builder()
                .setName("Hello World Root")
                .setTransactionQueue(basicTransactionQueue)
                .setCheckDependencies(new DefaultCheckDependencies())
                .addStatelessTriggerECD(triggerECD)
                .build();

        root.add(initiator);
        root.add(commit);

        initiator.trigger(triggerECD);
        basicTransactionQueue.waitTilEmpty();
    }
}
