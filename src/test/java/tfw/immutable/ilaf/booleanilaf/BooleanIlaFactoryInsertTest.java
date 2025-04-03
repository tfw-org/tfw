package tfw.immutable.ilaf.booleanilaf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BooleanIlaFactoryInsertTest {
    @Test
    void argumentTest() {
        assertThatThrownBy(() -> BooleanIlaFactoryInsert.create(null, 5, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilaFactory == null not allowed!");
    }

    @Test
    void createTest() {
        final BooleanIlaFactory f = BooleanIlaFactoryFill.create(false, 10);

        assertThat(BooleanIlaFactoryInsert.create(f, 5, true).create()).isNotNull();
    }
}
// AUTO GENERATED FROM TEMPLATE
