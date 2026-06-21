package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;

class DefaultComponentConfigTest {
    @Test
    void testDefaultComponentConfig() {
        final ObjectECD ecd = new ObjectECD("testECD");
        final Initiator initiator = Initiator.builder()
                .setName("testInitiator")
                .addEventChannelDescription(ecd)
                .build();

        DefaultComponentConfig<Initiator> config = new DefaultComponentConfig<>(initiator);

        assertThat(config.getComponent()).isSameAs(initiator);
    }

    @Test
    void testValidation() {
        assertThatThrownBy(() -> new DefaultComponentConfig<>(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("component");
    }
}
