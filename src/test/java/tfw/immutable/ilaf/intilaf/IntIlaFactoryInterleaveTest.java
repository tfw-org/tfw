package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);
        final IntIlaFactory[] factories = new IntIlaFactory[] {intIlaFactory, intIlaFactory};
        final int[] buffer = new int[10];

        assertThatThrownBy(() -> IntIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> IntIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);
        final IntIlaFactory[] f = new IntIlaFactory[] {intIlaFactory, intIlaFactory};
        final int[] b = new int[10];

        assertThat(IntIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
