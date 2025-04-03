package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaSegment;

public class BitIlaFactorySegment {
    private BitIlaFactorySegment() {}

    public static BitIlaFactory create(
            final BitIlaFactory bitIlaFactory, final long ilaOffsetInBits, final long ilaLengthInBits) {
        Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

        return () -> BitIlaSegment.create(bitIlaFactory.create(), ilaOffsetInBits, ilaLengthInBits);
    }
}
