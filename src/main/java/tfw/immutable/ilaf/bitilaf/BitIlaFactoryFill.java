package tfw.immutable.ilaf.bitilaf;

import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFill;

public class BitIlaFactoryFill {
    private BitIlaFactoryFill() {}

    public static BitIlaFactory create(final boolean value, final long lengthInBits) {
        final BitIlaFactory bitIlaFactoryImpl = new BitIlaFactoryImpl(value, lengthInBits);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final boolean value;
        private final long lengthInBits;

        public BitIlaFactoryImpl(final boolean value, final long lengthInBits) {
            this.value = value;
            this.lengthInBits = lengthInBits;
        }

        @Override
        public BitIla create() {
            return BitIlaFill.create(value, lengthInBits);
        }
    }
}
