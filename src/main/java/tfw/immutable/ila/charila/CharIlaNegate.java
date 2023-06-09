package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class CharIlaNegate {
    private CharIlaNegate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyCharIla(ila);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;

        MyCharIla(CharIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (char) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
