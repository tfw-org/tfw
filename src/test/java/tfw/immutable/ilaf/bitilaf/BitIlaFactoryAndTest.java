package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BitIlaFactoryAndTest {
    @Test
    void argumentTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThatThrownBy(() -> BitIlaFactoryAnd.create(null, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftBitIlaFactory == null not allowed!");
        assertThatThrownBy(() -> BitIlaFactoryAnd.create(bitIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightBitIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThat(BitIlaFactoryAnd.create(bitIlaFactory, bitIlaFactory).create())
                .isNotNull();
    }
}
