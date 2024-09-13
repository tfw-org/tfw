package tfw.immutable.iis.byteiis;

import java.io.IOException;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIisFromByteIla {
    private ByteIisFromByteIla() {}

    public static ByteIis create(final ByteIla ila) {
        return new ByteIisImpl(ila);
    }

    private static class ByteIisImpl extends AbstractByteIis {
        private final ByteIla ila;

        private long index = 0;

        public ByteIisImpl(final ByteIla ila) {
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(byte[] array, int offset, int length) throws IOException {
            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
