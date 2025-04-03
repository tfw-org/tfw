package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> LongIlaFactoryBound.create(null, 0L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory ilaFactory = LongIlaFactoryFill.create(0L, 10);
        final long min = 0L;
        final long max = 1L;

        assertThat(LongIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
