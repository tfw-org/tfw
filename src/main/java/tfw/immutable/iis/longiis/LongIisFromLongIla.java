package tfw.immutable.iis.longiis;

import java.io.IOException;
import tfw.immutable.ila.longila.LongIla;

public final class LongIisFromLongIla {
    private LongIisFromLongIla() {}

    public static LongIis create(final LongIla ila) {
        return new LongIisImpl(ila);
    }

    private static class LongIisImpl extends AbstractLongIis {
        private final LongIla ila;

        private long index = 0;

        public LongIisImpl(final LongIla ila) {
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
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
// AUTO GENERATED FROM TEMPLATE
