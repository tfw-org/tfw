package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);
        final DoubleIlaFactory[] factories = new DoubleIlaFactory[] {doubleIlaFactory, doubleIlaFactory};
        final double[] buffer = new double[10];

        assertThatThrownBy(() -> DoubleIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);
        final DoubleIlaFactory[] f = new DoubleIlaFactory[] {doubleIlaFactory, doubleIlaFactory};
        final double[] b = new double[10];

        assertThat(DoubleIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
