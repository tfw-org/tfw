package tfw.immutable.ilaf.shortilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ShortIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> ShortIlaFactoryDecimate.create(null, 2, new short[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final ShortIlaFactory factory = ShortIlaFactoryFill.create((short) 0, 10);

        assertThat(ShortIlaFactoryDecimate.create(factory, 2, new short[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
