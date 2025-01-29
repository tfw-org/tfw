package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaDecimate;

public class ByteIlaFactoryDecimate {
    private ByteIlaFactoryDecimate() {}

    public static ByteIlaFactory create(ByteIlaFactory ilaFactory, long factor, byte[] buffer) {
        return new ByteIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory ilaFactory;
        private final long factor;
        private final byte[] buffer;

        public ByteIlaFactoryImpl(final ByteIlaFactory ilaFactory, long factor, byte[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public ByteIla create() {
            return ByteIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
