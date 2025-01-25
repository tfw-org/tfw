package tfw.immutable.ilaf.longilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class LongIlaFactoryConcatenateTest {
    @Test
    void argumentTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);

        assertThatThrownBy(() -> LongIlaFactoryConcatenate.create(null, longIlaFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftFactory == null not allowed!");
        assertThatThrownBy(() -> LongIlaFactoryConcatenate.create(longIlaFactory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);

        assertThat(LongIlaFactoryConcatenate.create(longIlaFactory, longIlaFactory)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
