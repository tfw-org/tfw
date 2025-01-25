package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class BitIlaFactoryFromLongIlaFactory {
    private BitIlaFactoryFromLongIlaFactory() {}

    public static BitIlaFactory create(
            final LongIlaFactory longIlaFactory, final int firstLongOffsetInBits, final long lengthInBits) {
        final BitIlaFactory bitIlaFactoryImpl =
                new BitIlaFactoryImpl(longIlaFactory, firstLongOffsetInBits, lengthInBits);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int firstLongOffsetInBits;
        private final long lengthInBits;

        private BitIlaFactoryImpl(
                final LongIlaFactory longIlaFactory, final int firstLongOffsetInBits, final long lengthInBits) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.firstLongOffsetInBits = firstLongOffsetInBits;
            this.lengthInBits = lengthInBits;
        }

        @Override
        public BitIla create() {
            return BitIlaFromLongIla.create(longIlaFactory.create(), firstLongOffsetInBits, lengthInBits);
        }
    }
}
