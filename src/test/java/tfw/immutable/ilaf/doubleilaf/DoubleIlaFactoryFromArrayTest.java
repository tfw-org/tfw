package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class DoubleIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(DoubleIlaFactoryFromArray.create(new double[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
