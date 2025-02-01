package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class ByteIlaFactoryFromCastDoubleIlaFactory {
    private ByteIlaFactoryFromCastDoubleIlaFactory() {}

    public static ByteIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
