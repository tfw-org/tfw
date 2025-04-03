package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactoryDecimate.create(null, 2, new char[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory factory = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(CharIlaFactoryDecimate.create(factory, 2, new char[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
