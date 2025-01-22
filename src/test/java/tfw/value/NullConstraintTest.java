package tfw.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class NullConstraintTest {
    @Test
    void isCompatableTest() {
        NullConstraint nc = NullConstraint.INSTANCE;

        assertThatThrownBy(() -> nc.isCompatible(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("constraint == null not allowed!");

        assertThat(nc.isCompatible(nc)).isTrue();
    }

    @Test
    void getValueComplianceTest() {
        final NullConstraint nc = NullConstraint.INSTANCE;
        final Object v = new Object();

        assertThatThrownBy(() -> nc.checkValue(v))
                .isInstanceOf(ValueException.class)
                .hasMessage("Trigger event channels have no values, so no value complies with this constraint");

        nc.checkValue(null);
    }
}
