package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(BooleanIlaFactoryFill.create(false, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
