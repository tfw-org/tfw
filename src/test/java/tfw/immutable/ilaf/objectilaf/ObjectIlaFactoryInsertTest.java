package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ObjectIlaFactoryInsert.create(null, 5, Object.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ObjectIlaFactory<Object> f = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThat(ObjectIlaFactoryInsert.create(f, 5, String.class).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
