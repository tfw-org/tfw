package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);

        assertThatThrownBy(() -> IntIlaFactoryConcatenate.create(null, intIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> IntIlaFactoryConcatenate.create(intIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory f = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryConcatenate.create(f, f).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
