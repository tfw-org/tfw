package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryDivideTest {
    @Test
    void argumentTest() {
        final IntIlaFactory intIlaFactory = IntIlaFactoryFill.create(0, 10);

        assertThatThrownBy(() -> IntIlaFactoryDivide.create(null, intIlaFactory, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> IntIlaFactoryDivide.create(intIlaFactory, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory f = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryDivide.create(f, f, 10).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
