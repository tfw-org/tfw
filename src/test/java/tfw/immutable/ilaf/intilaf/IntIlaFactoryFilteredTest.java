package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIlaFiltered.IntFilter;

final class IntIlaFactoryFilteredTest {
    final IntFilter filter = new IntFilter() {
        @Override
        public boolean matches(int value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryFiltered.create(null, filter, new int[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryFiltered.create(intIlaFactory, filter, new int[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
