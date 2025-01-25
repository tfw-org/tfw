package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public class ByteIlaFactorySegment {
    private ByteIlaFactorySegment() {}

    public static ByteIlaFactory create(ByteIlaFactory factory, final long start, final long length) {
        return new ByteIlaFactoryImpl(factory, start, length);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory factory;
        private final long start;
        private final long length;

        public ByteIlaFactoryImpl(final ByteIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public ByteIla create() {
            return ByteIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
