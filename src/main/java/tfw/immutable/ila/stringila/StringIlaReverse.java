package tfw.immutable.ila.stringila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaReverse {
    private StringIlaReverse() {
        // non-instantiable class
    }

    public static StringIla create(StringIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyStringIla(ila);
    }

    private static class MyStringIla extends AbstractStringIla {
        private final StringIla ila;

        MyStringIla(StringIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(String[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
