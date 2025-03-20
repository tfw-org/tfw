package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryInsert.create(null, 5, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory f = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryInsert.create(f, 5, 1).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
