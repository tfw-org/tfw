package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(ShortIlaFactoryFromArray.create(new short[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
