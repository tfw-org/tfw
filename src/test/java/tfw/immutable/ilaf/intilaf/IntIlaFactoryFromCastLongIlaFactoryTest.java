package tfw.immutable.ilaf.intilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;
import tfw.immutable.ilaf.longilaf.LongIlaFactoryFill;

final class IntIlaFactoryFromCastLongIlaFactoryTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> IntIlaFactoryFromCastLongIlaFactory.create(null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIlaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final LongIlaFactory longIlaFactory = LongIlaFactoryFill.create(0L, 10);

        assertThat(IntIlaFactoryFromCastLongIlaFactory.create(longIlaFactory, 10)
                        .create())
                .isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
