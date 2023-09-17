package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaNegate {
    private CharIlaNegate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new CharIlaImpl(ila);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final CharIla ila;

        private CharIlaImpl(CharIla ila) {
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (char) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
