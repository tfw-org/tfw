package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaAnd;

public class BitIlaFactoryAnd {
    private BitIlaFactoryAnd() {}

    public static BitIlaFactory create(final BitIlaFactory leftBitIlaFactory, final BitIlaFactory rightBitIlaFactory) {
        Argument.assertNotNull(leftBitIlaFactory, "leftBitIlaFactory");
        Argument.assertNotNull(rightBitIlaFactory, "rightBitIlaFactory");

        return () -> BitIlaAnd.create(leftBitIlaFactory.create(), rightBitIlaFactory.create());
    }
}
