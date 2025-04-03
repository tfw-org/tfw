package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class CharIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(CharIlaFactoryFromArray.create(new char[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
