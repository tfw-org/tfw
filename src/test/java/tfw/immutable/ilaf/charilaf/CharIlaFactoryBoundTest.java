package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactoryBound.create(null, (char) 0, (char) 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory ilaFactory = CharIlaFactoryFill.create((char) 0, 10);
        final char min = (char) 0;
        final char max = (char) 1;

        assertThat(CharIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
