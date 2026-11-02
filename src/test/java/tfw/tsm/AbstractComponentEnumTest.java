package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;

class AbstractComponentEnumTest {
    @Test
    void testValues() {
        List<ComponentEnum<?>> channels = TestComponentEnum.values();

        assertThat(channels).hasSize(2);
        assertThat(channels.get(0)).isEqualTo(TestComponentEnum.INITIATOR1);
        assertThat(channels.get(1)).isEqualTo(TestComponentEnum.INITIATOR2);
    }

    static class TestComponentEnum<T extends TreeComponent> extends AbstractComponentEnum<T> {
        public static final TestComponentEnum<Initiator> INITIATOR1 = new TestComponentEnum<>(Initiator.builder()
                .setName("test1")
                .addEventChannelDescription(new ObjectECD("test1"))
                .build());
        public static final TestComponentEnum<Initiator> INITIATOR2 = new TestComponentEnum<>(Initiator.builder()
                .setName("test2")
                .addEventChannelDescription(new ObjectECD("test2"))
                .build());

        public TestComponentEnum(T component) {
            super(component);
        }

        @SuppressWarnings("unchecked")
        public static List<ComponentEnum<?>> values() {
            return valuesFromClass(TestComponentEnum.class);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void testValuesFromClassWhenNoInstances() {
        // Call valuesFromClass on a class that has no instances registered
        List<UnregisteredComponentEnum<?>> result =
                AbstractComponentEnum.valuesFromClass(UnregisteredComponentEnum.class);

        assertThat(result).isEmpty();
    }

    static class UnregisteredComponentEnum<T extends TreeComponent> extends AbstractComponentEnum<T> {
        public UnregisteredComponentEnum(T component) {
            super(component);
        }

        @SuppressWarnings("unchecked")
        public static List<EventChannelEnum<?>> values() {
            return valuesFromClass(UnregisteredComponentEnum.class);
        }
    }
}
