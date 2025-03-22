package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaXor;

public class BitIlaFactoryXor {
    private BitIlaFactoryXor() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
        Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

        return () -> BitIlaXor.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
    }
}
