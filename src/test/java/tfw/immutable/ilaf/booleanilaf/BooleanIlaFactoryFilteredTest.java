package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.booleanila.BooleanIlaFiltered.BooleanFilter;

final class BooleanIlaFactoryFilteredTest {
    final BooleanFilter filter = new BooleanFilter() {
        @Override
        public boolean matches(boolean value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BooleanIlaFactoryFiltered.create(null, filter, new boolean[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory booleanIlaFactory = BooleanIlaFactoryFill.create(false, 10);

        assertThat(BooleanIlaFactoryFiltered.create(booleanIlaFactory, filter, new boolean[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
