package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThatThrownBy(() -> ByteIlaFactoryConcatenate.create(null, byteIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFactoryConcatenate.create(byteIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory f = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(ByteIlaFactoryConcatenate.create(f, f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
