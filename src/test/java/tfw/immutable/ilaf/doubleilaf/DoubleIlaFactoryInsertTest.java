package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryInsert.create(null, 5, 0.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory f = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(DoubleIlaFactoryInsert.create(f, 5, 1.0).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
