package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryAddTest {
    @Test
    void argumentTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);

        assertThatThrownBy(() -> CharIlaFactoryAdd.create(null, charIlaFactory, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> CharIlaFactoryAdd.create(charIlaFactory, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory f = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(CharIlaFactoryAdd.create(f, f, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
