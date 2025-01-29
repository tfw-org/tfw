package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaBound;

public class ByteIlaFactoryBound {
    private ByteIlaFactoryBound() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, byte minimum, byte maximum) {
        return new ByteIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory ilaFactory;
        private final byte minimum;
        private final byte maximum;

        public ByteIlaFactoryImpl(final ByteIlaFactory ilaFactory, byte minimum, byte maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public ByteIla create() {
            return ByteIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
