package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class ByteIlaFactoryFromCastIntIlaFactory {
    private ByteIlaFactoryFromCastIntIlaFactory() {}

    public static ByteIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
