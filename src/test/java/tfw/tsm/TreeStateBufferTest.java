package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.StringECD;

final class TreeStateBufferTest {
    @Test
    void bufferTest() throws Exception {
        TreeStateBuffer tsb = new TreeStateBuffer();
        StringECD strECD = new StringECD("mystring");

        assertThatThrownBy(tsb::toTreeState)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The 'setName(String)' method must be called before this method.");
        assertThatThrownBy(() -> tsb.setName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> tsb.addState(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("state == null not allowed!");
        assertThatThrownBy(() -> tsb.addChild(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("child == null not allowed!");

        String name = "TestTreeState";
        tsb.setName(name);

        TreeState tstate = tsb.toTreeState();
        assertThat(tstate).isNotNull();
        assertThat(name).isEqualTo(tstate.getName());

        TreeState[] children = tstate.getChildren();
        assertThat(children).isNotNull();
        assertThat(children.length).isEqualTo(0);

        EventChannelState[] ecds = tstate.getState();
        assertThat(ecds).isNotNull();
        assertThat(ecds.length).isEqualTo(0);

        EventChannelState state = new EventChannelState(strECD, "Hello World");
        tsb.addState(state);
        tstate = tsb.toTreeState();
        ecds = tstate.getState();
        assertThat(ecds.length).isEqualTo(1);
        assertThat(state).isEqualTo(tstate.getState()[0]);

        tsb.setName("Parent");
        tsb.addChild(tstate);

        TreeState parent = tsb.toTreeState();
        children = parent.getChildren();
        assertThat(children.length).isEqualTo(1);
    }
}
