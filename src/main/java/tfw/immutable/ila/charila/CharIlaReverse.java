package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaReverse {
    private CharIlaReverse() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, final char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyCharIla(ila, buffer);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final char[] buffer;

        MyCharIla(CharIla ila, final char[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            final StridedCharIla stridedCharIla = new StridedCharIla(ila, buffer.clone());

            stridedCharIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
