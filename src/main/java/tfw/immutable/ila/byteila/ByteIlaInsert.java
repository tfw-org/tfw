package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaInsert {
    private ByteIlaInsert() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long index, byte value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");

        return new ByteIlaImpl(ila, index, value);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;
        private final long index;
        private final byte value;

        private ByteIlaImpl(ByteIla ila, long index, byte value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() + 1;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            final long startPlusLength = start + length;

            if (index < start) {
                ila.get(array, offset, start - 1, length);
            } else if (index >= startPlusLength) {
                ila.get(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.get(array, offset, start, indexMinusStart);
                }
                array[offset + indexMinusStart] = value;
                if (index < startPlusLength - 1) {
                    ila.get(array, offset + (indexMinusStart + 1), index, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
