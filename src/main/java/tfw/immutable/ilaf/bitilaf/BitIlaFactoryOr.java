package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaOr;

public class BitIlaFactoryOr {
    private BitIlaFactoryOr() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        final BitIlaFactory bitIlaFactoryImpl = new BitIlaFactoryImpl(leftBitIlaFactory, rightBitIlaFactory);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final BitIlaFactory leftBitIlaFactory;
        private final BitIlaFactory rightBitIlaFactory;

        public BitIlaFactoryImpl(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
            Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
            Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

            this.leftBitIlaFactory = leftBitIlaFactory;
            this.rightBitIlaFactory = rightBitIlaFactory;
        }

        @Override
        public BitIla create() {
            return BitIlaOr.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
        }
    }
}
