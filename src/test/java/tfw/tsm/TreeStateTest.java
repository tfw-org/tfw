package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class TreeStateTest {
    @Test
    void equalsTest() throws Exception {
        String name = "my tree";
        EventChannelState state = new EventChannelState(new StringECD("xyz"), "Hello");
        EventChannelState[] stateArray = new EventChannelState[] {};
        TreeState child = new TreeState("child", null, null);
        TreeState[] children = new TreeState[] {child};
        TreeState ts1 = new TreeState(name, stateArray, children);
        TreeState ts2 = new TreeState(name, stateArray, children);
        assertThat(ts1).isEqualTo(ts2).hasSameHashCodeAs(ts2).isNotNull().isNotEqualTo(new Object());

        ts2 = new TreeState("different", stateArray, children);
        assertThat(ts1).isNotEqualTo(ts2);

        ts2 = new TreeState(name, new EventChannelState[] {state}, children);
        assertThat(ts1).isNotEqualTo(ts2);

        ts2 = new TreeState(name, stateArray, new TreeState[0]);
        assertThat(ts1).isNotEqualTo(ts2);
    }
}
