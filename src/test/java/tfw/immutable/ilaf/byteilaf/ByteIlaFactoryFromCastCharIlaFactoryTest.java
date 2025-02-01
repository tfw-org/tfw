package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;
import tfw.immutable.ilaf.charilaf.CharIlaFactoryFill;

final class ByteIlaFactoryFromCastCharIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryFromCastCharIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("charIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(ByteIlaFactoryFromCastCharIlaFactory.create(charIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
