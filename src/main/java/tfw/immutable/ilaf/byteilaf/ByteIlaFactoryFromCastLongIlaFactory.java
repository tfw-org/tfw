package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class ByteIlaFactoryFromCastLongIlaFactory {
    private ByteIlaFactoryFromCastLongIlaFactory() {}

    public static ByteIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new ByteIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public ByteIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
