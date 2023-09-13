package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            final StridedShortIla stridedShortIla = StridedShortIlaFromShortIla.create(ila, buffer.clone());

            stridedShortIla.get(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
