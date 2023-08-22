package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaScalarMultiply {
    private CharIlaScalarMultiply() {
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

        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
