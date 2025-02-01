package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class ShortIlaFactoryFromCastByteIlaFactory {
    private ShortIlaFactoryFromCastByteIlaFactory() {}

    public static ShortIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(byteIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ByteIlaFactory byteIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final ByteIlaFactory byteIlaFactory, final int bufferSize) {
            Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

            this.byteIlaFactory = byteIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
