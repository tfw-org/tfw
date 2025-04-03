package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;
import tfw.immutable.ilaf.charilaf.CharIlaFactoryFill;

final class DoubleIlaFactoryFromCastCharIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryFromCastCharIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("charIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(DoubleIlaFactoryFromCastCharIlaFactory.create(charIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
