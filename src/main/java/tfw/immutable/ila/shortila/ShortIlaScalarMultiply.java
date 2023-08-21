package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaScalarMultiply {
    private ShortIlaScalarMultiply() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyShortIla(ila, scalar);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final short scalar;

        MyShortIla(ShortIla ila, short scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
