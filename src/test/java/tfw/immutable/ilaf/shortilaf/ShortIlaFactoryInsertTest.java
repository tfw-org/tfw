package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryInsert.create(null, 5, (short) 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory f = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(ShortIlaFactoryInsert.create(f, 5, (short) 1).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
