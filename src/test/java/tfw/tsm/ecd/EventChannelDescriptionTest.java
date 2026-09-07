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

        assertThat(ecd1).isEqualTo(ecd2).isNotNull();

        ecd2 = new TestECD("different", new IsAssignableFromPredicate(String.class));
        assertThat(ecd2).isNotEqualTo(ecd1);
    }

    private static class TestECD extends ObjectECD {
        public TestECD(String eventChannelName, Predicate<Object> predicate) {
            super(eventChannelName, predicate);
        }
    }
}
