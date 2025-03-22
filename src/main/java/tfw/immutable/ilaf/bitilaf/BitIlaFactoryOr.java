package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaOr;

public class BitIlaFactoryOr {
    private BitIlaFactoryOr() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
        Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

        return () -> BitIlaOr.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
    }
}
