package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0f, 10);

        assertThatThrownBy(() -> FloatIlaFactoryConcatenate.create(null, floatIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> FloatIlaFactoryConcatenate.create(floatIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory f = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryConcatenate.create(f, f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
