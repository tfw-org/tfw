package tfw.immutable.ilaf.charilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.charila.CharIlaFiltered.CharFilter;

final class CharIlaFactoryFilteredTest {
    final CharFilter filter = new CharFilter() {
        @Override
        public boolean matches(char value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> CharIlaFactoryFiltered.create(null, filter, new char[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final CharIlaFactory charIlaFactory = CharIlaFactoryFill.create((char) 0, 10);

        assertThat(CharIlaFactoryFiltered.create(charIlaFactory, filter, new char[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
