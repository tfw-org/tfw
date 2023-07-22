package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlaReverse {
    private CharIlaReverse() {
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
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
