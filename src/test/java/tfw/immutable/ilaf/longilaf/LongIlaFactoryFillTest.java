package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(LongIlaFactoryFill.create(0L, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
