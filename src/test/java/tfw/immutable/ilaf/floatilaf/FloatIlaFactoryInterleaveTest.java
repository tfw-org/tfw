package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0f, 10);
        final FloatIlaFactory[] factories = new FloatIlaFactory[] {floatIlaFactory, floatIlaFactory};
        final float[] buffer = new float[10];

        assertThatThrownBy(() -> FloatIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> FloatIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0f, 10);
        final FloatIlaFactory[] f = new FloatIlaFactory[] {floatIlaFactory, floatIlaFactory};
        final float[] b = new float[10];

        assertThat(FloatIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
