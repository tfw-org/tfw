package tfw.tsm.demo;

import com.google.common.flogger.FluentLogger;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;

public class HelloWorld {
    public static final String HELLO_WORLD_STRING = "HelloWorld!";
    private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

    public static final void main(String[] args) {
        final StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        final Initiator i = new Initiator("Hello World Initiator", new EventChannelDescription[] {triggerECD});
        final TriggeredCommit c = new TriggeredCommit("Hello World Commit", triggerECD, null, null) {
            @Override
            protected void commit() {
                LOGGER.atInfo().log(HELLO_WORLD_STRING);
            }
        };
        final Root r = Root.builder()
                .setName("Hello World Root")
                .addStatelessTriggerECD(triggerECD)
                .build();

        r.add(i);
        r.add(c);

        i.trigger(triggerECD);
    }
}
