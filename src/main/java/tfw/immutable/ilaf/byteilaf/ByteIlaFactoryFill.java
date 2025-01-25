package tfw.immutable.ilaf.byteilaf;

import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFill;

public class ByteIlaFactoryFill {
    private ByteIlaFactoryFill() {}

    public static ByteIlaFactory create(final byte value, final long length) {
        return new ByteIlaFactoryImpl(value, length);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final byte value;
        private final long length;

        public ByteIlaFactoryImpl(final byte value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public ByteIla create() {
            return ByteIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
