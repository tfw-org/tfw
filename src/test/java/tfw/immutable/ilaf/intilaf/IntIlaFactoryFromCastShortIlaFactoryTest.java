package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactoryFill;

final class IntIlaFactoryFromCastShortIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryFromCastShortIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("shortIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(IntIlaFactoryFromCastShortIlaFactory.create(shortIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
