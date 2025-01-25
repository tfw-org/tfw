package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(FloatIlaFactoryFill.create(0.0f, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
