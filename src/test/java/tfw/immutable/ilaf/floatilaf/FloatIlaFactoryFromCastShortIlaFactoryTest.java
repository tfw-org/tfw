package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactoryFill;

final class FloatIlaFactoryFromCastShortIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryFromCastShortIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("shortIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(FloatIlaFactoryFromCastShortIlaFactory.create(shortIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
