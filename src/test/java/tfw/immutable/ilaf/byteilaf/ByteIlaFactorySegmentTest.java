package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactorySegmentTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactorySegment.create(null, 0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory factory = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(ByteIlaFactorySegment.create(factory, 0, 5).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
