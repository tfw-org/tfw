package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryDecimate.create(null, 2, new float[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory factory = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryDecimate.create(factory, 2, new float[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
