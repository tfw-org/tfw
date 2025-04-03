package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryDecimate.create(null, 2, new byte[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory factory = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(ByteIlaFactoryDecimate.create(factory, 2, new byte[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
