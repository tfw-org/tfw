package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final BooleanIlaFactory booleanIlaFactory = BooleanIlaFactoryFill.create(false, 10);
        final BooleanIlaFactory[] factories = new BooleanIlaFactory[] {booleanIlaFactory, booleanIlaFactory};
        final boolean[] buffer = new boolean[10];

        assertThatThrownBy(() -> BooleanIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory booleanIlaFactory = BooleanIlaFactoryFill.create(false, 10);
        final BooleanIlaFactory[] f = new BooleanIlaFactory[] {booleanIlaFactory, booleanIlaFactory};
        final boolean[] b = new boolean[10];

        assertThat(BooleanIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
