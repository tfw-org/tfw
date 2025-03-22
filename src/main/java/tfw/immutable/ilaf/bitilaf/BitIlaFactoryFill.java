package tfw.immutable.ilaf.bitilaf;

import tfw.immutable.ila.bitila.BitIlaFill;

public class BitIlaFactoryFill {
    private BitIlaFactoryFill() {}

    public static BitIlaFactory create(final boolean value, final long lengthInBits) {
        return () -> BitIlaFill.create(value, lengthInBits);
    }
}
