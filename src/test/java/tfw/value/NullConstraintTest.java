package tfw.value;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 */
class NullConstraintTest {
    @Test
    void testIsCompatable() {
        NullConstraint nc = NullConstraint.INSTANCE;

        assertThrows(IllegalArgumentException.class, () -> nc.isCompatible(null));

        assertTrue(nc.isCompatible(nc), "isCompatible() rejected itself");
    }

    @Test
    void testgetValueCompliance() {
        NullConstraint nc = NullConstraint.INSTANCE;

        final Object v = new Object();

        assertThrows(ValueException.class, () -> nc.checkValue(v));

        nc.checkValue(null);
    }
}
