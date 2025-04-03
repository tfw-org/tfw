package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.LongIlaFromBitIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ilaf.bitilaf.BitIlaFactory;

public class LongIlaFactoryFromBitIlaFactory {
    private LongIlaFactoryFromBitIlaFactory() {}

    public static LongIlaFactory create(final BitIlaFactory bitIlaFactory) {
        return new LongIlaFactoryImpl(bitIlaFactory);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final BitIlaFactory bitIlaFactory;

        private LongIlaFactoryImpl(final BitIlaFactory bitIlaFactory) {
            Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

            this.bitIlaFactory = bitIlaFactory;
        }

        @Override
        public LongIla create() {
            return LongIlaFromBitIla.create(bitIlaFactory.create());
        }
    }
}
