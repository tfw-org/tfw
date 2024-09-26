package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

class TriggeredConverterTest {
    private final String answer = "Hello World";
    private String triggerA = null;
    private String triggerB = null;
    private String debugTriggerA = null;
    private String debugTriggerB = null;
    private StringECD channel1 = new StringECD("1");
    private StringECD channel2 = new StringECD("2");
    private StatelessTriggerECD trigger = new StatelessTriggerECD("trigger");
    private ObjectECD[] sinks = new ObjectECD[] {channel1, channel2};
    private EventChannelDescription[] sources = new EventChannelDescription[] {channel1, channel2, trigger};
    private Initiator initiator = new Initiator("Initiator", sources);
    private TriggeredConverter triggeredConverter =
            new TriggeredConverter("TriggeredConverter", trigger, sinks, sources) {
                @Override
                protected void convert() {
                    // System.out.println("triggerAction");
                    triggerA = (String) get(channel1);
                    triggerB = (String) get(channel2);
                }

                @Override
                protected void debugConvert() {
                    // System.out.println("debugTriggerAction");
                    debugTriggerA = (String) get(channel1);
                    debugTriggerB = (String) get(channel2);
                }
            };

    @Test
    void testConverter() throws Exception {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(channel1);
        rf.addEventChannel(channel2);
        rf.addEventChannel(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();

        Root root = rf.create("Test branch", queue);
        root.add(initiator);
        root.add(triggeredConverter);

        initiator.set(channel1, answer);
        queue.waitTilEmpty();
        assertEquals(null, triggerA, "send a triggerA");
        assertEquals(null, triggerB, "send a triggerB");
        assertEquals(null, debugTriggerA, "send a debugTriggerA");
        assertEquals(null, debugTriggerB, "send a debugTriggerB");

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals(null, triggerA, "send a triggerA");
        assertEquals(null, triggerB, "send a triggerB");
        assertEquals(answer, debugTriggerA, "send a debugTriggerA");
        assertEquals(null, debugTriggerB, "send a debugTriggerB");

        debugTriggerA = null;
        initiator.set(channel2, answer);
        queue.waitTilEmpty();
        assertEquals(null, triggerA, "send a triggerA");
        assertEquals(null, triggerB, "send a triggerB");
        assertEquals(null, debugTriggerA, "send a debugTriggerA");
        assertEquals(null, debugTriggerB, "send a debugTriggerB");

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals(answer, triggerA, "send a triggerA");
        assertEquals(answer, triggerB, "send a triggerB");
        assertEquals(null, debugTriggerA, "send a debugTriggerA");
        assertEquals(null, debugTriggerB, "send a debugTriggerB");
    }
}
