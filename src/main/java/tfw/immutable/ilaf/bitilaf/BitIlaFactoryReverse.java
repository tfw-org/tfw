package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaReverse;

public class BitIlaFactoryReverse {
    private BitIlaFactoryReverse() {}

    public static BitIlaFactory create(final BitIlaFactory bitIlaFactory) {
        Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

        return () -> BitIlaReverse.create(bitIlaFactory.create());
    }
}
