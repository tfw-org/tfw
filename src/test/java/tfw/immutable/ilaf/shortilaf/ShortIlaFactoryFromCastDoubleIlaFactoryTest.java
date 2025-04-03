package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactoryFill;

final class ShortIlaFactoryFromCastDoubleIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryFromCastDoubleIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("doubleIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(ShortIlaFactoryFromCastDoubleIlaFactory.create(doubleIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
