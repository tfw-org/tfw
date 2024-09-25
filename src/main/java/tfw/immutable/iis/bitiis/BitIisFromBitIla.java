package tfw.immutable.iis.bitiis;

import java.io.IOException;
import tfw.check.Argument;
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
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.lengthInBits();
        }

        @Override
        protected long readImpl(long[] array, long offsetInBits, long lengthInBits) throws IOException {
            if (index == ila.lengthInBits()) {
                return -1;
            }

            final int elementsToGet = (int) Math.min(ila.lengthInBits() - index, lengthInBits);

            ila.get(array, (int) offsetInBits, index, elementsToGet);

            index += elementsToGet;

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            if (index == ila.lengthInBits()) {
                return -1;
            }

            final long originalIndex = index;

            index = Math.min(ila.lengthInBits(), index + n);

            return index - originalIndex;
        }
    }
}
