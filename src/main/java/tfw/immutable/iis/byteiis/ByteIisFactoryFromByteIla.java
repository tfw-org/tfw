package tfw.immutable.iis.byteiis;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIisFactoryFromByteIla {
    private ByteIisFactoryFromByteIla() {}

    public static ByteIisFactory create(final ByteIla ila) {
        return new ByteIisFactoryImpl(ila);
    }

    private static class ByteIisFactoryImpl implements ByteIisFactory {
        private final ByteIla ila;

        public ByteIisFactoryImpl(final ByteIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public ByteIis create() {
            return ByteIisFromByteIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
