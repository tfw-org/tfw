package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

class AbstractEventChannelEnumTest {
    @Test
    void testValues() {
        List<EventChannelEnum<?>> channels = TestEventChannelEnum.values();

        assertThat(channels).hasSize(2);
        assertThat(channels.get(0)).isEqualTo(TestEventChannelEnum.TRIGGER);
        assertThat(channels.get(1)).isEqualTo(TestEventChannelEnum.OBJECT);
    }

    static class TestEventChannelEnum<T extends EventChannelDescription> extends AbstractEventChannelEnum<T> {
        public static final TestEventChannelEnum<StatelessTriggerECD> TRIGGER =
                new TestEventChannelEnum<>(new StatelessTriggerECD("trigger"), AlwaysChangeRule.RULE, null, null);
        public static final TestEventChannelEnum<ObjectECD> OBJECT =
                new TestEventChannelEnum<>(new ObjectECD("object"), DotEqualsRule.RULE, null, null);

        public TestEventChannelEnum(T ecd, StateChangeRule rule, Object state, String[] tags) {
            super(ecd, rule, state, tags);
        }

        @SuppressWarnings("unchecked")
        public static List<EventChannelEnum<?>> values() {
            return valuesFromClass(TestEventChannelEnum.class);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void testValuesFromClassWhenNoInstances() {
        // Call valuesFromClass on a class that has no instances registered
        List<UnregisteredEventChannelEnum<?>> result =
                AbstractEventChannelEnum.valuesFromClass(UnregisteredEventChannelEnum.class);

        assertThat(result).isEmpty();
    }

    static class UnregisteredEventChannelEnum<T extends EventChannelDescription> extends AbstractEventChannelEnum<T> {
        public UnregisteredEventChannelEnum(T ecd) {
            super(ecd, DotEqualsRule.RULE, null, null);
        }

        @SuppressWarnings("unchecked")
        public static List<EventChannelEnum<?>> values() {
            return valuesFromClass(UnregisteredEventChannelEnum.class);
        }
    }
}
