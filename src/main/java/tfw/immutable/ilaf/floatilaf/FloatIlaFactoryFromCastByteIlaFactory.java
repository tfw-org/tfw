package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class FloatIlaFactoryFromCastByteIlaFactory {
    private FloatIlaFactoryFromCastByteIlaFactory() {}

    public static FloatIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(byteIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final ByteIlaFactory byteIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final ByteIlaFactory byteIlaFactory, final int bufferSize) {
            Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

            this.byteIlaFactory = byteIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
