package tfw.immutable.ilaf.bitilaf;

import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaNot;

public class BitIlaFactoryNot {
    private BitIlaFactoryNot() {}

    public static BitIlaFactory create(final BitIlaFactory bitIlaFactory) {
        final BitIlaFactory bitIlaFactoryImpl = new BitIlaFactoryImpl(bitIlaFactory);

        bitIlaFactoryImpl.create();

        return bitIlaFactoryImpl;
    }

    private static class BitIlaFactoryImpl implements BitIlaFactory {
        private final BitIlaFactory bitIlaFactory;

        public BitIlaFactoryImpl(final BitIlaFactory bitIlaFactory) {
            Argument.assertNotNull(bitIlaFactory, "bitIlaFactory");

            this.bitIlaFactory = bitIlaFactory;
        }

        @Override
        public BitIla create() {
            return BitIlaNot.create(bitIlaFactory.create());
        }
    }
}
