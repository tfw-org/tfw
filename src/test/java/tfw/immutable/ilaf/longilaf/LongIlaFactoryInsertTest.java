package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> LongIlaFactoryInsert.create(null, 5, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory f = LongIlaFactoryFill.create(0L, 10);

        assertThat(LongIlaFactoryInsert.create(f, 5, 1L).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
