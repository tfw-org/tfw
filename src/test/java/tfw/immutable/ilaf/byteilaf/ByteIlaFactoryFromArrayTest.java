package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ByteIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(ByteIlaFactoryFromArray.create(new byte[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
