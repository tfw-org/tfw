package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryInvertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryInvert.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory f = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryInvert.create(f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
