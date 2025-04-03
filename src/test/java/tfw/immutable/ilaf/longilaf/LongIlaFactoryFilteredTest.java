package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFiltered.LongFilter;

final class LongIlaFactoryFilteredTest {
    final LongFilter filter = new LongFilter() {
        @Override
        public boolean matches(long value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> LongIlaFactoryFiltered.create(null, filter, new long[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);

        assertThat(LongIlaFactoryFiltered.create(longIlaFactory, filter, new long[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
