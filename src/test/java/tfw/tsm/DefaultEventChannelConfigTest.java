package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

class DefaultEventChannelConfigTest {
    @Test
    void testDefaultEventChannelConfig() {
        ObjectECD ecd = new ObjectECD("ecd");
        StatelessTriggerECD triggerEcd = new StatelessTriggerECD("trigger");
        StateChangeRule rule = AlwaysChangeRule.RULE;
        Object initialState = "initialState";
        String[] tags = {"tag1", "tag2"};

        DefaultEventChannelConfig<ObjectECD> config = new DefaultEventChannelConfig<>(ecd, rule, initialState, tags);

        assertThat(ecd).isEqualTo(config.getEventChannelDescription());
        assertThat(rule).isEqualTo(config.getStateChangeRule());
        assertThat(initialState).isEqualTo(config.getInitialState());
        assertThat(tags).containsExactlyInAnyOrder(config.getExportTags());

        DefaultEventChannelConfig<StatelessTriggerECD> triggerConfig =
                new DefaultEventChannelConfig<>(triggerEcd, rule, null, null);
        assertThat(triggerEcd).isEqualTo(triggerConfig.getEventChannelDescription());
        assertThat(triggerConfig.getStateChangeRule()).isEqualTo(rule);
        assertThat(triggerConfig.getInitialState()).isNull();
        assertThat(triggerConfig.getExportTags()).isNull();
    }

    @Test
    void testValidation() {
        ObjectECD objectEcd = new ObjectECD("ecd");
        StatelessTriggerECD triggerEcd = new StatelessTriggerECD("trigger");
        StateChangeRule rule = AlwaysChangeRule.RULE;
        Object initialState = "initialState";
        String[] tags = {"tag1", "tag2"};

        assertThatThrownBy(() -> new DefaultEventChannelConfig<>(null, rule, initialState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ecd");
        assertThatThrownBy(() -> new DefaultEventChannelConfig<>(triggerEcd, null, initialState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rule");
        assertThatThrownBy(() -> new DefaultEventChannelConfig<>(objectEcd, null, initialState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rule");
    }
}
