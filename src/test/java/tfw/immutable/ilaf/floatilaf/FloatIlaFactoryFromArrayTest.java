package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class FloatIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(FloatIlaFactoryFromArray.create(new float[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
