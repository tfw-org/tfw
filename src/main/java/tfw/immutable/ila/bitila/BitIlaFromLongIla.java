package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;

public final class BitIlaFromLongIla {
    private BitIlaFromLongIla() {}

    public static BitIla create(final LongIla longIla, final int firstLongOffsetInBits, final long lengthInBits) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(firstLongOffsetInBits, 0, "firstLongOffsetInBits");
        Argument.assertNotGreaterThan(firstLongOffsetInBits, 63, "firstLongOffsetInBits");
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");

        return new BitIlaImpl(longIla, firstLongOffsetInBits, lengthInBits);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final LongIla longIla;
        private final int firstLongOffsetInBits;
        private final long lengthInBits;

        public BitIlaImpl(final LongIla longIla, final int firstLongOffsetInBits, final long lengthInBits) {
            this.longIla = longIla;
            this.firstLongOffsetInBits = firstLongOffsetInBits;
            this.lengthInBits = lengthInBits;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return lengthInBits;
        }

        @Override
        protected void getImpl(
                final long[] array, final int arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
                throws IOException {
            final long ilaStartInLongs = ilaStartInBits / Long.SIZE;
            final long ilaEndInLongs = (ilaStartInBits + lengthInBits) / Long.SIZE;
            final int lengthInLongs = (int) (ilaEndInLongs - ilaStartInLongs) + 1;
            final long[] tempArray = new long[lengthInLongs];

            longIla.get(tempArray, 0, ilaStartInLongs, lengthInLongs);

            BitIlaUtil.copy(array, arrayOffsetInBits, tempArray, firstLongOffsetInBits, lengthInBits);
        }
    }
}
