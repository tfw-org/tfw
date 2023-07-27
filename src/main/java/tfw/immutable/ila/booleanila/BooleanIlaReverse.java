package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class BooleanIlaReverse {
    private BooleanIlaReverse() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, final boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyBooleanIla(ila, buffer);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final boolean[] buffer;

        MyBooleanIla(BooleanIla ila, final boolean[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(boolean[] array, int offset, long start, int length) throws DataInvalidException {
            final StridedBooleanIla stridedBooleanIla = new StridedBooleanIla(ila, buffer.clone());

            stridedBooleanIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
