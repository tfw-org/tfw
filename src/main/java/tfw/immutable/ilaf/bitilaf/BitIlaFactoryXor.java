package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaXor;

public class BitIlaFactoryXor {
    private BitIlaFactoryXor() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        final BitIlaFactory bitIlaFactoryImpl = new BitIlaFactoryImpl(leftBitIlaFactory, rightBitIlaFactory);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final BitIlaFactory leftBitIlaFactory;
        private final BitIlaFactory rightBitIlaFactory;

        private BitIlaFactoryImpl(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
            Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
            Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

            this.leftBitIlaFactory = leftBitIlaFactory;
            this.rightBitIlaFactory = rightBitIlaFactory;
        }

        @Override
        public BitIla create() {
            return BitIlaXor.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
        }
    }
}
