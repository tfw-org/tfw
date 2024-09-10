package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;

public final class BitIlaSegment {
    private BitIlaSegment() {}

    public static BitIla create(final BitIla bitIla, final long ilaOffsetInBits, final long ilaLengthInBits) {
        return new BitIlaImpl(bitIla, ilaOffsetInBits, ilaLengthInBits);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final BitIla bitIla;
        private final long ilaOffsetInBits;
        private final long ilaLengthInBits;

        public BitIlaImpl(final BitIla bitIla, final long ilaOffsetInBits, final long ilaLengthInBits) {
            Argument.assertNotNull(bitIla, "bitIla");
            Argument.assertNotLessThan(ilaOffsetInBits, 0, "ilaOffsetInBits");
            Argument.assertNotLessThan(ilaLengthInBits, 0, "ilaLengthInBits");

            this.bitIla = bitIla;
            this.ilaOffsetInBits = ilaOffsetInBits;
            this.ilaLengthInBits = ilaLengthInBits;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ilaLengthInBits;
        }

        @Override
        protected void getImpl(long[] array, int arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            bitIla.get(array, arrayOffsetInBits, ilaOffsetInBits + ilaStartInBits, lengthInBits);
        }
    }
}
