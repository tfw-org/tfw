package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastByteIla;
import tfw.immutable.ilaf.byteilaf.ByteIlaFactory;

public class IntIlaFactoryFromCastByteIlaFactory {
    private IntIlaFactoryFromCastByteIlaFactory() {}

    public static IntIlaFactory create(ByteIlaFactory byteIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(byteIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final ByteIlaFactory byteIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final ByteIlaFactory byteIlaFactory, final int bufferSize) {
            Argument.assertNotNull(byteIlaFactory, "byteIlaFactory");

            this.byteIlaFactory = byteIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastByteIla.create(byteIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
