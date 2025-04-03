package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryDecimateTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryDecimate.create(null, 2, new int[10]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory factory = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryDecimate.create(factory, 2, new int[10]).create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
