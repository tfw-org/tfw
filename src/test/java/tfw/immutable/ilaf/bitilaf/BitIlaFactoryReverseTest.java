package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BitIlaFactoryReverseTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BitIlaFactoryReverse.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bitIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThat(BitIlaFactoryReverse.create(bitIlaFactory).create()).isNotNull();
    }
}
