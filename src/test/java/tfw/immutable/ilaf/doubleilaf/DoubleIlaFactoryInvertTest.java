package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryInvertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryInvert.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory f = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(DoubleIlaFactoryInvert.create(f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
