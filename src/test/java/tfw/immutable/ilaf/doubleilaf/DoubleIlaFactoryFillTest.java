package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(DoubleIlaFactoryFill.create(0.0, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
