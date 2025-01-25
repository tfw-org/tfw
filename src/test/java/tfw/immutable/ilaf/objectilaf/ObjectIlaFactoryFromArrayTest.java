package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryArrayTest {
    @Test
    void createTest() {
        assertThat(ObjectIlaFactoryFromArray.create(new Object[10]).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
