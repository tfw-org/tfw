package tfw.immutable.ilaf.floatilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.floatila.FloatIlaFiltered.FloatFilter;

final class FloatIlaFactoryFilteredTest {
    final FloatFilter filter = new FloatFilter() {
        @Override
        public boolean matches(float value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> FloatIlaFactoryFiltered.create(null, filter, new float[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final FloatIlaFactory floatIlaFactory = FloatIlaFactoryFill.create(0.0f, 10);

        assertThat(FloatIlaFactoryFiltered.create(floatIlaFactory, filter, new float[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
