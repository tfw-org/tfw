package tfw.tsm;

import java.util.List;
import org.junit.jupiter.api.Test;
import tfw.tsm.AbstractEventChannelEnumTest.TestEventChannelEnum;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

class AbstractMultiplexerEnumTest {
    @Test
    void testGetEventChannels() {

        List<EventChannelEnum<EventChannelDescription>> channels =
                new TestEventChannelEnum<>(null, null, null, null).values();

        // assertThat(channels).hasSize(3);
        // assertThat(channels.get(0)).isEqualTo(TestMultiplexerEnum.E1);
        // assertThat(channels.get(1)).isEqualTo(TestMultiplexerEnum.E2);
    }

    static class TestMultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
            extends AbstractMultiplexerEnum<T, U> {
        public static final TestMultiplexerEnum<ObjectECD, ObjectIlaECD> E1 = new TestMultiplexerEnum<>(
                new ObjectECD("O1"),
                new ObjectIlaECD("M1"),
                new ObjectIlaMultiplexerStrategy(),
                null,
                DotEqualsRule.RULE,
                null);
        public static final TestMultiplexerEnum<ObjectECD, ObjectIlaECD> E2 = new TestMultiplexerEnum<>(
                new ObjectECD("O2"),
                new ObjectIlaECD("M2"),
                new ObjectIlaMultiplexerStrategy(),
                null,
                DotEqualsRule.RULE,
                null);

        public TestMultiplexerEnum(
                T ecd, U mecd, MultiplexerStrategy strategy, StateChangeRule rule, Object state, String[] tags) {
            super(ecd, mecd, strategy, rule, state, tags);
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<MultiplexerEnum<T, U>> getValues() {
            return AbstractMultiplexerEnum.valuesFromClass(TestMultiplexerEnum.class);
        }
    }
}
