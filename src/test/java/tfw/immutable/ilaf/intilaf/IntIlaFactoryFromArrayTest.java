package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(IntIlaFactoryFromArray.create(new int[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
