package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.shortila.ShortIlaFiltered.ShortFilter;

final class ShortIlaFactoryFilteredTest {
    final ShortFilter filter = new ShortFilter() {
        @Override
        public boolean matches(short value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryFiltered.create(null, filter, new short[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory shortIlaFactory = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(ShortIlaFactoryFiltered.create(shortIlaFactory, filter, new short[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
