package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);
        final LongIlaFactory[] factories = new LongIlaFactory[] {longIlaFactory, longIlaFactory};
        final long[] buffer = new long[10];

        assertThatThrownBy(() -> LongIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> LongIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);
        final LongIlaFactory[] f = new LongIlaFactory[] {longIlaFactory, longIlaFactory};
        final long[] b = new long[10];

        assertThat(LongIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
