package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryFillTest {
    @Test
    void createTest() {
        assertThat(ObjectIlaFactoryFill.create(Object.class, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
