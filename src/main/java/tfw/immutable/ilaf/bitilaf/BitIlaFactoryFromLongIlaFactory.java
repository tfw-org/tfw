package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class BitIlaFactoryFromLongIlaFactory {
    private BitIlaFactoryFromLongIlaFactory() {}

    public static BitIlaFactory create(
            final LongIlaFactory longIlaFactory, final int firstLongOffsetInBits, final long lengthInBits) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> BitIlaFromLongIla.create(longIlaFactory.create(), firstLongOffsetInBits, lengthInBits);
    }
}
