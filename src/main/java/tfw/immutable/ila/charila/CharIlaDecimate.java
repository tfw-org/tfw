package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaDecimate {
    private CharIlaDecimate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long factor, char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new CharIlaImpl(ila, factor, buffer);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final CharIla ila;
        private final long factor;
        private final char[] buffer;

        private CharIlaImpl(CharIla ila, long factor, char[] buffer) {
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final CharIla segment = CharIlaSegment.create(ila, segmentStart, segmentLength);
            final CharIlaIterator fi = new CharIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
