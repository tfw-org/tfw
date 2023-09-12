package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaFiltered {
    private ByteIlaFiltered() {
        // non-instantiable class
    }

    public interface ByteFilter {
        boolean matches(byte value);
    }

    public static ByteIla create(ByteIla ila, ByteFilter filter, byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyByteIla(ila, filter, buffer);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final ByteFilter filter;
        private final byte[] buffer;

        private MyByteIla(ByteIla ila, ByteFilter filter, byte[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            ByteIlaIterator oii = new ByteIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator oii = new ByteIlaIterator(ByteIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                byte node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
