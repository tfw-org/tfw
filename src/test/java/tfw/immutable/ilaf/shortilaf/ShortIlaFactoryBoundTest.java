package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryBoundTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryBound.create(null, (short) 0, (short) 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory ilaFactory = ShortIlaFactoryFill.create((short) 0, 10);
        final short min = (short) 0;
        final short max = (short) 1;

        assertThat(ShortIlaFactoryBound.create(ilaFactory, min, max).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
