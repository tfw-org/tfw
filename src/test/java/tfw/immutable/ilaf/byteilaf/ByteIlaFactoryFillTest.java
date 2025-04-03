package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(ByteIlaFactoryFill.create((byte) 0, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
