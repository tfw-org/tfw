package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.value.ClassValueConstraint;
import tfw.value.ValueConstraint;

final class EventChannelDescriptionTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new TestECD(null, ClassValueConstraint.STRING))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName == null not allowed!");
        assertThatThrownBy(() -> new TestECD(" ", ClassValueConstraint.STRING))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName.trim().length() == 0 not allowed!");
        assertThatThrownBy(() -> new TestECD("A", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("constraint == null not allowed!");
    }

    @Test
    void equalsTest() {
        TestECD ecd1 = new TestECD("A", ClassValueConstraint.STRING);
        TestECD ecd2 = new TestECD("A", ClassValueConstraint.STRING);

        assertThat(ecd1).isEqualTo(ecd2).isNotNull();

        ecd2 = new TestECD("different", ClassValueConstraint.STRING);
        assertThat(ecd2).isNotEqualTo(ecd1);
    }

    private static class TestECD extends ObjectECD {
        public TestECD(String eventChannelName, ValueConstraint<String> constraint) {
            super(eventChannelName, constraint);
        }
    }
}
