package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(ShortIlaFactoryFill.create((short) 0, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
