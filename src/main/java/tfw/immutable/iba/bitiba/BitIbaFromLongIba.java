package tfw.immutable.iba.bitiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.longiba.LongIba;
import tfw.immutable.ila.bitila.BitIlaUtil;

public final class BitIbaFromLongIba {
    private static final BigInteger LONG_SIZE = BigInteger.valueOf(Long.SIZE);

    private BitIbaFromLongIba() {}

    public static BitIba create(final LongIba longIba, final int firstLongOffsetInBits, final BigInteger lengthInBits) {
        return new BitIbaImpl(longIba, firstLongOffsetInBits, lengthInBits);
    }

    private static class BitIbaImpl extends AbstractBitIba {
        private final LongIba longIba;
        private final int firstLongOffsetInBits;
        private final BigInteger lengthInBits;

        public BitIbaImpl(final LongIba longIba, final int firstLongOffsetInBits, final BigInteger lengthInBits) {
            Arguments.checkNotNull(longIba, "longIba");
            Arguments.checkGreaterThanOrEqual(firstLongOffsetInBits, "firstLongOffsetInBits", 0, "0");
            Arguments.checkLessThanOrEqual(firstLongOffsetInBits, "firstLongOffsetInBits", 63, "63");
            Arguments.checkNotNull(lengthInBits, "lengthInBits");
            Arguments.checkGreaterThanOrEqual(lengthInBits, "lengthInBits", BigInteger.ZERO, "0");

            this.longIba = longIba;
            this.firstLongOffsetInBits = firstLongOffsetInBits;
            this.lengthInBits = lengthInBits;
        }

        @Override
        protected void closeImpl() throws IOException {
            longIba.close();
        }

        @Override
        protected BigInteger lengthInBitsImpl() throws IOException {
            return lengthInBits;
        }

        @Override
        protected void getImpl(
                final long[] array,
                final long arrayOffsetInBits,
                final BigInteger ibaStartInBits,
                final long lengthInBits)
                throws IOException {
            final BigInteger ibaStartInLongs = ibaStartInBits.divide(LONG_SIZE);
            final BigInteger ibaEndInLongs =
                    ibaStartInBits.add(BigInteger.valueOf(lengthInBits)).divide(LONG_SIZE);
            final int lengthInLongs = ibaEndInLongs
                    .subtract(ibaStartInLongs)
                    .add(BigInteger.ONE)
                    .min(BigInteger.valueOf(Integer.MAX_VALUE))
                    .intValue();
            final long[] tempArray = new long[lengthInLongs];

            longIba.get(tempArray, 0, ibaStartInLongs, lengthInLongs);

            BitIlaUtil.copy(array, arrayOffsetInBits, tempArray, firstLongOffsetInBits, lengthInBits);
        }
    }
}
