package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);
        final ShortIlaFactory[] factories = new ShortIlaFactory[] {shortIlaFactory, shortIlaFactory};
        final short[] buffer = new short[10];

        assertThatThrownBy(() -> ShortIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> ShortIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);
        final ShortIlaFactory[] f = new ShortIlaFactory[] {shortIlaFactory, shortIlaFactory};
        final short[] b = new short[10];

        assertThat(ShortIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
