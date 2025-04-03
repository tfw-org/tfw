package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryInterleaveTest {
    @Test
    void argumentTest() {
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);
        final ByteIlaFactory[] factories = new ByteIlaFactory[] {byteIlaFactory, byteIlaFactory};
        final byte[] buffer = new byte[10];

        assertThatThrownBy(() -> ByteIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);
        final ByteIlaFactory[] f = new ByteIlaFactory[] {byteIlaFactory, byteIlaFactory};
        final byte[] b = new byte[10];

        assertThat(ByteIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
