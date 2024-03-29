package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaDecimate {
    private ShortIlaDecimate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long factor, short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new ShortIlaImpl(ila, factor, buffer);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ShortIla ila;
        private final long factor;
        private final short[] buffer;

        private ShortIlaImpl(ShortIla ila, long factor, short[] buffer) {
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ShortIla segment = ShortIlaSegment.create(ila, segmentStart, segmentLength);
            final ShortIlaIterator fi = new ShortIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
