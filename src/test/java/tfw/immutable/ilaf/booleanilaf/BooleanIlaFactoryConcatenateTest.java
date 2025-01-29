package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final BooleanIlaFactory booleanIlaFactory = BooleanIlaFactoryFill.create(false, 10);

        assertThatThrownBy(() -> BooleanIlaFactoryConcatenate.create(null, booleanIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaFactoryConcatenate.create(booleanIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory f = BooleanIlaFactoryFill.create(false, 10);

        assertThat(BooleanIlaFactoryConcatenate.create(f, f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
