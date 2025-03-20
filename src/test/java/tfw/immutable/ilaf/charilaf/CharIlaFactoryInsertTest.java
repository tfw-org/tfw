package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactoryInsert.create(null, 5, (char) 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory f = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(CharIlaFactoryInsert.create(f, 5, (char) 1).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
