package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryBound.create(null, 0.0, 1.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory ilaFactory = DoubleIlaFactoryFill.create(0.0, 10);
        final double min = 0.0;
        final double max = 1.0;

        assertThat(DoubleIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
