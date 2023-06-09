package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaReverse {
    private IntIlaReverse() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;

        MyIntIla(IntIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
