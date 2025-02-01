package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class LongIlaFactoryFromCastByteIlaFactory {
    private LongIlaFactoryFromCastByteIlaFactory() {}

    public static LongIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(byteIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final ByteIlaFactory byteIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final ByteIlaFactory byteIlaFactory, final int bufferSize) {
            Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

            this.byteIlaFactory = byteIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
