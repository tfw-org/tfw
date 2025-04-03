package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(CharIlaFactoryFill.create((char) 0, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
