package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class ShortIlaFromCastByteIla {
    private ShortIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static ShortIla create(ByteIla byteIla, int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(byteIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyShortIla(ByteIla byteIla, int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length();
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator fi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
