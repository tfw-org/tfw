package tfw.immutable.ilaf.byteilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.byteila.ByteIlaFiltered.ByteFilter;

final class ByteIlaFactoryFilteredTest {
    final ByteFilter filter = new ByteFilter() {
        @Override
        public boolean matches(byte value) {
            return true;
        }
    };

    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ByteIlaFactoryFiltered.create(null, filter, new byte[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ByteIlaFactory byteIlaFactory = ByteIlaFactoryFill.create((byte) 0, 10);

        assertThat(ByteIlaFactoryFiltered.create(byteIlaFactory, filter, new byte[10])
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
