package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryAddTest {
    @Test
    void argumentTest() {
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);

        assertThatThrownBy(() -> DoubleIlaFactoryAdd.create(null, doubleIlaFactory, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaFactoryAdd.create(doubleIlaFactory, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory f = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(DoubleIlaFactoryAdd.create(f, f, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
