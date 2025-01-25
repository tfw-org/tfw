package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaSegment;

public class BitIlaFactorySegment {
    private BitIlaFactorySegment() {}

    public static BitIlaFactory create(
            final BitIlaFactory bitIlaFactory, final long ilaOffsetInBits, final long ilaLengthInBits) {
        final BitIlaFactory bitIlaFactoryImpl = new BitIlaFactoryImpl(bitIlaFactory, ilaOffsetInBits, ilaLengthInBits);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final BitIlaFactory bitIlaFactory;
        private final long ilaOffsetInBits;
        private final long ilaLengthInBits;

        private BitIlaFactoryImpl(
                final BitIlaFactory bitIlaFactory, final long ilaOffsetInBits, final long ilaLengthInBits) {
            Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

            this.bitIlaFactory = bitIlaFactory;
            this.ilaOffsetInBits = ilaOffsetInBits;
            this.ilaLengthInBits = ilaLengthInBits;
        }

        @Override
        public BitIla create() {
            return BitIlaSegment.create(bitIlaFactory.create(), ilaOffsetInBits, ilaLengthInBits);
        }
    }
}
