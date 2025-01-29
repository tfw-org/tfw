package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryDecimate.create(null, 2, new double[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory factory = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(DoubleIlaFactoryDecimate.create(factory, 2, new double[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
