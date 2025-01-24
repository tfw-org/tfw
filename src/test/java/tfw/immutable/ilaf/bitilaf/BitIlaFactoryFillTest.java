package tfw.immutable.ilaf.bitilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class BitIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(BitIlaFactoryFill.create(false, 10).create()).isNotNull();
    }
}
