package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BitIlaFactoryNotTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BitIlaFactoryNot.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bitIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThat(BitIlaFactoryNot.create(bitIlaFactory).create()).isNotNull();
    }
}
