package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (char) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
