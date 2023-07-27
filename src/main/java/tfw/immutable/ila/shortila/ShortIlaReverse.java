package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaReverse {
    private ShortIlaReverse() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, final short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyShortIla(ila, buffer);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final short[] buffer;

        MyShortIla(ShortIla ila, final short[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws DataInvalidException {
            final StridedShortIla stridedShortIla = new StridedShortIla(ila, buffer.clone());

            stridedShortIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
