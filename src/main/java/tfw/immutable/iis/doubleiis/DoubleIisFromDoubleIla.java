package tfw.immutable.iis.doubleiis;

import java.io.IOException;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIisFromDoubleIla {
    private DoubleIisFromDoubleIla() {}

    public static DoubleIis create(final DoubleIla ila) {
        return new DoubleIisImpl(ila);
    }

    private static class DoubleIisImpl extends AbstractDoubleIis {
        private final DoubleIla ila;

        private long index = 0;

        public DoubleIisImpl(final DoubleIla ila) {
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(double[] array, int offset, int length) throws IOException {
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
