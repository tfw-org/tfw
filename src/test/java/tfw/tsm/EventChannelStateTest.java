package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class EventChannelStateTest {
    @Test
    void eventChannelStateTest() throws Exception {
        StringECD stringECD = new StringECD("myChannel");
        String value = "Hello World";

        assertThatThrownBy(() -> new EventChannelState(null, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ecd == null not allowed!");

        EventChannelState state = new EventChannelState(stringECD, value);
        assertThat(stringECD.getEventChannelName()).isEqualTo(state.getEventChannelName());
        assertThat(value).isEqualTo(state.getState());
    }

    @Test
    void equalsTest() throws Exception {
        StringECD stringECD = new StringECD("myChannel");
        String value = "Hello World";

        EventChannelState state1 = new EventChannelState(stringECD, value);
        EventChannelState state2 = new EventChannelState(stringECD, value);
        assertThat(state1).isEqualTo(state2);
        assertThat(state1.hashCode()).hasSameHashCodeAs(state2.hashCode());
        assertThat(state1).isNotNull().isNotEqualTo(new Object());

        state2 = new EventChannelState(new StringECD("different"), value);
        assertThat(state1).isNotEqualTo(state2);

        state2 = new EventChannelState(stringECD, "different");
        assertThat(state1).isNotEqualTo(state2);
    }
}
