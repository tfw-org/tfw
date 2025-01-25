package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(LongIlaFactoryFromArray.create(new long[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
