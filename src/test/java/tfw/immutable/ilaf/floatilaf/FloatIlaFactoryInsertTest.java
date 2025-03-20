package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryInsert.create(null, 5, 0.0f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory f = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryInsert.create(f, 5, 1.0f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
