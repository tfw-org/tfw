package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ObjectIlaFactoryDecimate.create(null, 2, new Object[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ObjectIlaFactory<Object> factory = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThat(ObjectIlaFactoryDecimate.create(factory, 2, new Object[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
