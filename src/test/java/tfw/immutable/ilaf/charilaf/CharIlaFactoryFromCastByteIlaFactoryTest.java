package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactoryFill;

final class CharIlaFactoryFromCastByteIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactoryFromCastByteIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("byteIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(CharIlaFactoryFromCastByteIlaFactory.create(byteIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
