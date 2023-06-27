package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaReverse {
    private BooleanIlaReverse() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyBooleanIla(ila);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;

        MyBooleanIla(BooleanIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
