package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

final class TriggeredConverterTest {
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
                    triggerA = (String) get(channel1);
                    triggerB = (String) get(channel2);
                }

                @Override
                protected void debugConvert() {
                    debugTriggerA = (String) get(channel1);
                    debugTriggerB = (String) get(channel2);
                }
            };

    @Test
    void converterTest() {
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
        assertThat(triggerA).isNull();
        assertThat(triggerB).isNull();
        assertThat(debugTriggerA).isNull();
        assertThat(debugTriggerB).isNull();

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertThat(triggerA).isNull();
        assertThat(triggerB).isNull();
        assertThat(debugTriggerA).isEqualTo(answer);
        assertThat(debugTriggerB).isNull();

        debugTriggerA = null;
        initiator.set(channel2, answer);
        queue.waitTilEmpty();
        assertThat(triggerA).isNull();
        assertThat(triggerB).isNull();
        assertThat(debugTriggerA).isNull();
        assertThat(debugTriggerB).isNull();

        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertThat(triggerA).isEqualTo(answer);
        assertThat(triggerB).isEqualTo(answer);
        assertThat(debugTriggerA).isNull();
        assertThat(debugTriggerB).isNull();
    }
}
