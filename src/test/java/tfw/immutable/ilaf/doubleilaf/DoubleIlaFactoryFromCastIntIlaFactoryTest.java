package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.immutable.ilaf.intilaf.IntIlaFactoryFill;

final class DoubleIlaFactoryFromCastIntIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryFromCastIntIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("intIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory ilaFactory = IntIlaFactoryFill.create(0, 10);

        assertThat(DoubleIlaFactoryFromCastIntIlaFactory.create(ilaFactory, 10).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
