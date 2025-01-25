package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactorySegmentTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactorySegment.create(null, 0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory factory = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactorySegment.create(factory, 0, 5).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
