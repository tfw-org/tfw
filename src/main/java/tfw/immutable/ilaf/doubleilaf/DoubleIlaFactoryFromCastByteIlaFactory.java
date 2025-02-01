package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class DoubleIlaFactoryFromCastByteIlaFactory {
    private DoubleIlaFactoryFromCastByteIlaFactory() {}

    public static DoubleIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(byteIlaFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final ByteIlaFactory byteIlaFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(final ByteIlaFactory byteIlaFactory, final int bufferSize) {
            Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

            this.byteIlaFactory = byteIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
