package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThatThrownBy(() -> ObjectIlaFactoryConcatenate.create(null, objectIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaFactoryConcatenate.create(objectIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThat(ObjectIlaFactoryConcatenate.create(objectIlaFactory, objectIlaFactory)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
