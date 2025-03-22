package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaConcatenate;

public class BitIlaFactoryConcatenate {
    private BitIlaFactoryConcatenate() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
        Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

        return () -> BitIlaConcatenate.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
    }
}
