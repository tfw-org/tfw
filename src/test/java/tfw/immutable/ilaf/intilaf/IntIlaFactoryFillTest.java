package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(IntIlaFactoryFill.create(0, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
