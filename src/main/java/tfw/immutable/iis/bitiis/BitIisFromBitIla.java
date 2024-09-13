package tfw.immutable.iis.bitiis;

import java.io.IOException;
import tfw.immutable.ila.bitila.BitIla;

public final class BitIisFromBitIla {
    private BitIisFromBitIla() {}

    public static BitIis create(final BitIla ila) {
        return new BitIisImpl(ila);
    }

    private static class BitIisImpl extends AbstractBitIis {
        private final BitIla ila;

        private long index = 0;

        public BitIisImpl(final BitIla ila) {
            this.ila = ila;
        }

        @Override
        public void close() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(long[] array, int offset, int length) throws IOException {
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
