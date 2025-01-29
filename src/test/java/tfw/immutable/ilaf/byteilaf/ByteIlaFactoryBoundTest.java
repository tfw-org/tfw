package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryBound.create(null, (byte) 0, (byte) 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory ilaFactory = ByteIlaFactoryFill.create((byte) 0, 10);
        final byte min = (byte) 0;
        final byte max = (byte) 1;

        assertThat(ByteIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
