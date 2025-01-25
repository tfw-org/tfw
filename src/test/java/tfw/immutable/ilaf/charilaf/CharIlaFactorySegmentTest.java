package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class CharIlaFactorySegmentTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactorySegment.create(null, 0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory factory = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(CharIlaFactorySegment.create(factory, 0, 5).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
