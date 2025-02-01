package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryDivideTest {
    @Test
    void argumentTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0f, 10);

        assertThatThrownBy(() -> FloatIlaFactoryDivide.create(null, floatIlaFactory, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> FloatIlaFactoryDivide.create(floatIlaFactory, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory f = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryDivide.create(f, f, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
