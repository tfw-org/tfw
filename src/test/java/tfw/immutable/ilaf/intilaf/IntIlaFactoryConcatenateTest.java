package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class IntIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final IntIlaFactory boolinteanIlaFactory = IntIlaFactoryFill.create(0, 10);

        assertThatThrownBy(() -> IntIlaFactoryConcatenate.create(null, boolinteanIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> IntIlaFactoryConcatenate.create(boolinteanIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final IntIlaFactory boolinteanIlaFactory = IntIlaFactoryFill.create(0, 10);

        assertThat(IntIlaFactoryConcatenate.create(boolinteanIlaFactory, boolinteanIlaFactory)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
