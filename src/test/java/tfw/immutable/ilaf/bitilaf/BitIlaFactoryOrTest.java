package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BitIlaFactoryOrTest {
    @Test
    void argumentTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThatThrownBy(() -> BitIlaFactoryOr.create(null, bitIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftBitIlaFactory == null not allowed!");
        assertThatThrownBy(() -> BitIlaFactoryOr.create(bitIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("righttBitIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThat(BitIlaFactoryOr.create(bitIlaFactory, bitIlaFactory).create())
                .isNotNull();
    }
}
