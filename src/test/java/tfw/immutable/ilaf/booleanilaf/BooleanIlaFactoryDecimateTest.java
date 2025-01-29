package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BooleanIlaFactoryDecimate.create(null, 2, new boolean[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory factory = BooleanIlaFactoryFill.create(false, 10);

        assertThat(BooleanIlaFactoryDecimate.create(factory, 2, new boolean[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
