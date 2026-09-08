package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

final class EventChannelDescriptionTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new TestECD(null, new IsAssignableFromPredicate(String.class)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName == null not allowed!");

        assertThatThrownBy(() -> new TestECD(" ", new IsAssignableFromPredicate(String.class)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName.trim().length() == 0 not allowed!");

        assertThatThrownBy(() -> new TestECD("A", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("predicate == null not allowed!");
    }

    @Test
    void equalsTest() {
        TestECD ecd1 = new TestECD("A", new IsAssignableFromPredicate(String.class));
        TestECD ecd2 = new TestECD("A", new IsAssignableFromPredicate(String.class));

        assertThat(ecd1).isEqualTo(ecd2);
        assertThat(ecd2).isEqualTo(ecd1);
        assertThat(ecd1.hashCode()).isEqualTo(ecd2.hashCode());

        ecd2 = new TestECD("different", new IsAssignableFromPredicate(String.class));

        assertThat(ecd1).isNotEqualTo(ecd2);

        ecd2 = new TestECD("A", new IsAssignableFromPredicate(Integer.class));

        assertThat(ecd1).isNotEqualTo(ecd2);
    }

    @Test
    void statelessTriggerEqualsTest() {
        StatelessTriggerECD ecd1 = new StatelessTriggerECD("trigger");
        StatelessTriggerECD ecd2 = new StatelessTriggerECD("trigger");

        assertThat(ecd1).isEqualTo(ecd2);
        assertThat(ecd2).isEqualTo(ecd1);
        assertThat(ecd1.hashCode()).isEqualTo(ecd2.hashCode());
    }

    @Test
    void integerRangeEqualsTest() {
        IntegerECD ecd1 = new IntegerECD("value", 0, 100);
        IntegerECD ecd2 = new IntegerECD("value", 0, 100);

        assertThat(ecd1).isEqualTo(ecd2);
        assertThat(ecd2).isEqualTo(ecd1);
        assertThat(ecd1.hashCode()).isEqualTo(ecd2.hashCode());

        ecd2 = new IntegerECD("value", 0, 101);

        assertThat(ecd1).isNotEqualTo(ecd2);
    }

    private static class TestECD extends ObjectECD {
        public TestECD(String eventChannelName, Predicate<Object> predicate) {
            super(eventChannelName, predicate);
        }
    }
}
