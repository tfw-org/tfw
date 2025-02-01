package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;
import tfw.immutable.ilaf.intilaf.IntIlaFactoryFill;

final class ShortIlaFactoryFromCastIntIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryFromCastIntIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("intIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory ilaFactory = IntIlaFactoryFill.create(0, 10);

        assertThat(ShortIlaFactoryFromCastIntIlaFactory.create(ilaFactory, 10).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
