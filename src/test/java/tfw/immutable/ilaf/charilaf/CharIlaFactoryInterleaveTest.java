package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);
        final CharIlaFactory[] factories = new CharIlaFactory[] {charIlaFactory, charIlaFactory};
        final char[] buffer = new char[10];

        assertThatThrownBy(() -> CharIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> CharIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);
        final CharIlaFactory[] f = new CharIlaFactory[] {charIlaFactory, charIlaFactory};
        final char[] b = new char[10];

        assertThat(CharIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
