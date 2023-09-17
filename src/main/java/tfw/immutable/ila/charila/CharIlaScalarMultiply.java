package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaScalarMultiply {
    private CharIlaScalarMultiply() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, char scalar) {
        Argument.assertNotNull(ila, "ila");

        return new CharIlaImpl(ila, scalar);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final CharIla ila;
        private final char scalar;

        private CharIlaImpl(CharIla ila, char scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
