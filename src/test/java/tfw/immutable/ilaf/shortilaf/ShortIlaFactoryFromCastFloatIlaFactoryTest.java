package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactoryFill;

final class ShortIlaFactoryFromCastFloatIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryFromCastFloatIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("floatIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0F, 10);

        assertThat(ShortIlaFactoryFromCastFloatIlaFactory.create(floatIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
