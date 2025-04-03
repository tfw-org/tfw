package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(BooleanIlaFactoryFromArray.create(new boolean[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
