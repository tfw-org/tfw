package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;
import tfw.immutable.ilaf.bitilaf.BitIlaFactoryFill;

final class LongIlaFactoryFromBitIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> LongIlaFactoryFromBitIlaFactory.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bitIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);

        assertThat(LongIlaFactoryFromBitIlaFactory.create(bitIlaFactory).create())
                .isNotNull();
    }
}
