package tfw.immutable.ilaf.doubleilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIlaFiltered.DoubleFilter;

final class DoubleIlaFactoryFilteredTest {
    final DoubleFilter filter = new DoubleFilter() {
        @Override
        public boolean matches(double value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> DoubleIlaFactoryFiltered.create(null, filter, new double[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final DoubleIlaFactory doubleIlaFactory = DoubleIlaFactoryFill.create(0.0, 10);

        assertThat(DoubleIlaFactoryFiltered.create(doubleIlaFactory, filter, new double[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
