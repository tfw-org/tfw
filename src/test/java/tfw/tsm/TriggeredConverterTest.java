package tfw.tsm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

public class TriggeredConverterTest extends TestCase {
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
                protected void convert() {
                    // System.out.println("triggerAction");
                    triggerA = (String) get(channel1);
                    triggerB = (String) get(channel2);
                }

                protected void debugConvert() {
                    // System.out.println("debugTriggerAction");
                    debugTriggerA = (String) get(channel1);
                    debugTriggerB = (String) get(channel2);
                }
            };

    public void testConverter() throws Exception {
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
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", answer, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        debugTriggerA = null;
        initiator.set(channel2, answer);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", null, triggerA);
        assertEquals("send a triggerB", null, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertEquals("send a triggerA", answer, triggerA);
        assertEquals("send a triggerB", answer, triggerB);
        assertEquals("send a debugTriggerA", null, debugTriggerA);
        assertEquals("send a debugTriggerB", null, debugTriggerB);
    }

    public static Test suite() {
        return new TestSuite(TriggeredConverterTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
