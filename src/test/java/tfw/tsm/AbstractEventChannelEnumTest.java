package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

class AbstractEventChannelEnumTest {
    @Test
    void testGetEventChannels() {

        List<EventChannelEnum<EventChannelDescription>> channels =
                new TestEventChannelEnum<>(null, null, null, null).values();

        assertThat(channels).hasSize(2);
        assertThat(channels.get(0)).isEqualTo(TestEventChannelEnum.TRIGGER);
        assertThat(channels.get(1)).isEqualTo(TestEventChannelEnum.OBJECT);
    }

    static class TestEventChannelEnum<T extends EventChannelDescription> extends AbstractEventChannelEnum<T> {
        public static final TestEventChannelEnum<StatelessTriggerECD> TRIGGER =
                new TestEventChannelEnum<>(new StatelessTriggerECD("trigger"), null, null, null);
        public static final TestEventChannelEnum<ObjectECD> OBJECT =
                new TestEventChannelEnum<>(new ObjectECD("object"), DotEqualsRule.RULE, null, null);

        public TestEventChannelEnum(T ecd, StateChangeRule rule, Object state, String[] tags) {
            super(ecd, rule, state, tags);
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<EventChannelEnum<T>> values() {
            return AbstractEventChannelEnum.valuesFromClass(TestEventChannelEnum.class);
        }
    }
}
