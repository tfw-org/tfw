package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;

class DefaultMultiplexerConfigTest {
    @Test
    void testDefaultMultiplexerConfig() {
        ObjectECD ecd = new ObjectECD("ecd");
        ObjectECD mecd = new ObjectECD("mecd");
        MultiplexerStrategy strategy = new ObjectIlaMultiplexerStrategy();
        StateChangeRule rule = AlwaysChangeRule.RULE;
        Object initialMultiState = "initialState";
        String[] tags = {"tag1", "tag2"};

        DefaultMultiplexerConfig<EventChannelDescription, EventChannelDescription> config =
                new DefaultMultiplexerConfig<>(ecd, mecd, strategy, rule, initialMultiState, tags);

        assertThat(ecd).isEqualTo(config.getEventChannelDescription());
        assertThat(mecd).isEqualTo(config.getMultiEventChannelDescription());
        assertThat(strategy).isEqualTo(config.getMultiplexerStrategy());
        assertThat(initialMultiState).isEqualTo(config.getInitialMultiState());
        assertThat(rule).isEqualTo(config.getStateChangeRule());
        assertThat(tags).containsExactlyInAnyOrder(config.getExportTags());
    }

    @Test
    void testValidation() {
        ObjectECD ecd = new ObjectECD("ecd");
        ObjectECD mecd = new ObjectECD("mecd");
        MultiplexerStrategy strategy = new ObjectIlaMultiplexerStrategy();
        StateChangeRule rule = AlwaysChangeRule.RULE;
        Object initialMultiState = "initialState";
        String[] tags = {"tag1", "tag2"};

        assertThatThrownBy(() -> new DefaultMultiplexerConfig<>(null, mecd, strategy, rule, initialMultiState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ecd");
        assertThatThrownBy(() -> new DefaultMultiplexerConfig<>(ecd, null, strategy, rule, initialMultiState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mecd");
        assertThatThrownBy(() -> new DefaultMultiplexerConfig<>(ecd, mecd, null, rule, initialMultiState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("strategy");
        assertThatThrownBy(() -> new DefaultMultiplexerConfig<>(ecd, mecd, strategy, null, initialMultiState, tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rule");
    }
}
