package tfw.immutable.ilaf.objectilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIlaFiltered.ObjectFilter;

final class ObjectIlaFactoryFilteredTest {
    final ObjectFilter<Object> filter = new ObjectFilter<Object>() {
        @Override
        public boolean matches(Object value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ObjectIlaFactoryFiltered.create(null, filter, new Object[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ObjectIlaFactory<Object> objectIlaFactory = ObjectIlaFactoryFill.create(Object.class, 10);

        assertThat(ObjectIlaFactoryFiltered.create(objectIlaFactory, filter, new Object[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
