package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryInsert.create(null, 5, (byte) 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory f = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(ByteIlaFactoryInsert.create(f, 5, (byte) 1).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
