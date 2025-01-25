package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFromBitIlaFactory;

final class BitIlaFactoryFromLongIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BitIlaFactoryFromLongIlaFactory.create(null, 0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BitIlaFactory bitIlaFactory = BitIlaFactoryFill.create(false, 10);
        final LongIlaFactory longIlaFactory = LongIlaFactoryFromBitIlaFactory.create(bitIlaFactory);

        assertThat(BitIlaFactoryFromLongIlaFactory.create(longIlaFactory, 0, 10).create())
                .isNotNull();
    }
}
