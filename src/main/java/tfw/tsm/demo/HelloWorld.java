package tfw.tsm.demo;

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class HelloWorld {
    public static final void main(String[] args) {
        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");

        RootFactory rf = new RootFactory();
        rf.addEventChannel(triggerECD);
        Root r = rf.create("Hello World Root", new BasicTransactionQueue());

        Initiator i = new Initiator("Hello World Initiator", new EventChannelDescription[] {triggerECD});

        TriggeredCommit c = new TriggeredCommit("Hello World Commit", triggerECD, null, null) {
            @Override
            protected void commit() {
                System.out.println("HelloWorld");
            }
        };

        r.add(i);
        r.add(c);

        i.trigger(triggerECD);
    }
}
