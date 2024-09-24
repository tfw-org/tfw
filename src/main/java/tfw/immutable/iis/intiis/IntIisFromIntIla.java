package tfw.immutable.iis.intiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;

public final class IntIisFromIntIla {
    private IntIisFromIntIla() {}

    public static IntIis create(final IntIla ila) {
        return new IntIisImpl(ila);
    }

    private static class IntIisImpl extends AbstractIntIis {
        private final IntIla ila;

        private long index = 0;

        public IntIisImpl(final IntIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(int[] array, int offset, int length) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            index += elementsToGet;

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
