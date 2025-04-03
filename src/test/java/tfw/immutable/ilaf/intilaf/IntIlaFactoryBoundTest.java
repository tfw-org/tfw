package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryBound.create(null, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory ilaFactory = IntIlaFactoryFill.create(0, 10);
        final int min = 0;
        final int max = 1;

        assertThat(IntIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
