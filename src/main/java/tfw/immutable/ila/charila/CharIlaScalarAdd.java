package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class CharIlaScalarAdd {
    private CharIlaScalarAdd() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, char scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyCharIla(ila, scalar);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final char scalar;

        MyCharIla(CharIla ila, char scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
