package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactorySegmentTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BooleanIlaFactorySegment.create(null, 0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factory == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory factory = BooleanIlaFactoryFill.create(false, 10);

        assertThat(BooleanIlaFactorySegment.create(factory, 0, 5).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
