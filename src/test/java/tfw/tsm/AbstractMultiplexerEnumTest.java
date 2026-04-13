package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

class AbstractMultiplexerEnumTest {
    @Test
    void testValues() {
        List<MultiplexerEnum<?, ?>> channels = TestMultiplexerEnum.values();

        assertThat(channels).hasSize(2);
        assertThat(channels.get(0)).isEqualTo(TestMultiplexerEnum.E1);
        assertThat(channels.get(1)).isEqualTo(TestMultiplexerEnum.E2);
    }

    static class TestMultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
            extends AbstractMultiplexerEnum<T, U> {
        public static final TestMultiplexerEnum<ObjectECD, ObjectIlaECD> E1 = new TestMultiplexerEnum<>(
                new ObjectECD("O1"),
                new ObjectIlaECD("M1"),
                new ObjectIlaMultiplexerStrategy(),
                DotEqualsRule.RULE,
                null,
                null);
        public static final TestMultiplexerEnum<ObjectECD, ObjectIlaECD> E2 = new TestMultiplexerEnum<>(
                new ObjectECD("O2"),
                new ObjectIlaECD("M2"),
                new ObjectIlaMultiplexerStrategy(),
                DotEqualsRule.RULE,
                null,
                null);

        public TestMultiplexerEnum(
                T ecd, U mecd, MultiplexerStrategy strategy, StateChangeRule rule, Object state, String[] tags) {
            super(ecd, mecd, strategy, rule, state, tags);
        }

        @SuppressWarnings("unchecked")
        public static List<MultiplexerEnum<?, ?>> values() {
            return valuesFromClass(TestMultiplexerEnum.class);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void testValuesFromClassWhenNoInstances() {
        // Call valuesFromClass on a class that has no instances registered
        List<?> result = AbstractMultiplexerEnum.valuesFromClass(UnregisteredMultiplexerEnum.class);

        assertThat(result).isEmpty();
    }

    static class UnregisteredMultiplexerEnum<T extends EventChannelDescription, U extends EventChannelDescription>
            extends AbstractMultiplexerEnum<T, U> {
        public UnregisteredMultiplexerEnum(T ecd, U mecd) {
            super(ecd, mecd, new ObjectIlaMultiplexerStrategy(), DotEqualsRule.RULE, null, null);
        }

        @SuppressWarnings("unchecked")
        public static List<MultiplexerEnum<?, ?>> values() {
            return valuesFromClass(UnregisteredMultiplexerEnum.class);
        }
    }
}
