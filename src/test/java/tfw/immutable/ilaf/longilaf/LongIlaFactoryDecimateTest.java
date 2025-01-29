package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> LongIlaFactoryDecimate.create(null, 2, new long[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory factory = LongIlaFactoryFill.create(0L, 10);

        assertThat(LongIlaFactoryDecimate.create(factory, 2, new long[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
