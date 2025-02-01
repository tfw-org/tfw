package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactoryFill;

final class ByteIlaFactoryFromCastShortIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryFromCastShortIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("shortIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(ByteIlaFactoryFromCastShortIlaFactory.create(shortIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
