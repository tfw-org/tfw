package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ObjectIlaFactorySegmentTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ObjectIlaFactorySegment.create(null, 0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factory == null not allowed!");
    }

    @Test
    void createTest() {
        final ObjectIlaFactory<Object> factory = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThat(ObjectIlaFactorySegment.create(factory, 0, 5).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
