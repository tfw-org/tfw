package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaNot;

public class BitIlaFactoryNot {
    private BitIlaFactoryNot() {}

    public static BitIlaFactory create(final BitIlaFactory bitIlaFactory) {
        Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

        return () -> BitIlaNot.create(bitIlaFactory.create());
    }
}
