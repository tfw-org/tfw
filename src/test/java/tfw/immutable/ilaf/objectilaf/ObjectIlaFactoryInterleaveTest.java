package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryInterleaveTest {
    @SuppressWarnings("unchecked")
    @Test
    void argumentTest() {
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(Object.class, 10);
        final ObjectIlaFactory<Object>[] factories = new ObjectIlaFactory[] {objectIlaFactory, objectIlaFactory};
        final Object[] buffer = new Object[10];

        assertThatThrownBy(() -> ObjectIlaFactoryInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactories == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaFactoryInterleave.create(factories, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffers == null not allowed!");
    }

    @SuppressWarnings("unchecked")
    @Test
    void createTest() {
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(Object.class, 10);
        final ObjectIlaFactory<Object>[] f = new ObjectIlaFactory[] {objectIlaFactory, objectIlaFactory};
        final Object[] b = new Object[10];

        assertThat(ObjectIlaFactoryInterleave.create(f, b).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
