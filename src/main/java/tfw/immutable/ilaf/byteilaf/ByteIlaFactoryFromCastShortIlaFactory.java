package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class ByteIlaFactoryFromCastShortIlaFactory {
    private ByteIlaFactoryFromCastShortIlaFactory() {}

    public static ByteIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(shortIlaFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ShortIlaFactory shortIlaFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(final ShortIlaFactory shortIlaFactory, final int bufferSize) {
            Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

            this.shortIlaFactory = shortIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
