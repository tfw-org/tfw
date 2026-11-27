package tfw.immutable.iba.bitiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.longiba.LongIba;
import tfw.immutable.ila.bitila.BitIlaUtil;

public final class BitIbaFromLongIba {
    private static final BigInteger LONG_SIZE = BigInteger.valueOf(Long.SIZE);
    private static final BigInteger LONG_SIZE_MINUS_ONE = LONG_SIZE.subtract(BigInteger.ONE);

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
            Arguments.checkGreaterThanOrEqual(firstLongOffsetInBits, "firstLongOffsetInBits", 0);
            Arguments.checkLessThanOrEqual(firstLongOffsetInBits, "firstLongOffsetInBits", 63);
            Arguments.checkNotNull(lengthInBits, "lengthInBits");
            Arguments.checkGreaterThanOrEqual(lengthInBits, "lengthInBits", BigInteger.ZERO);

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
            final int ibaStartOffsetInBits = ibaStartInBits.remainder(LONG_SIZE).intValueExact();
            final int sourceOffsetInBits = firstLongOffsetInBits + ibaStartOffsetInBits;

            final BigInteger requiredLengthInLongs = BigInteger.valueOf(sourceOffsetInBits)
                    .add(BigInteger.valueOf(lengthInBits))
                    .add(LONG_SIZE_MINUS_ONE)
                    .divide(LONG_SIZE);
            final int lengthInLongs = requiredLengthInLongs.intValueExact();

            final long[] tempArray = new long[lengthInLongs];

            longIba.get(tempArray, 0, ibaStartInLongs, lengthInLongs);

            BitIlaUtil.copy(array, arrayOffsetInBits, tempArray, sourceOffsetInBits, lengthInBits);
        }
    }
}
