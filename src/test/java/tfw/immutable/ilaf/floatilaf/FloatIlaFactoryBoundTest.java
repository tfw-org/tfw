package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryBound.create(null, 0.0f, 1.0f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory ilaFactory = FloatIlaFactoryFill.create(0.0f, 10);
        final float min = 0.0f;
        final float max = 1.0f;

        assertThat(FloatIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
