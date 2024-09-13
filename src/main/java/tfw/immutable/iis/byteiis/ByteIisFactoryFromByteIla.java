package tfw.immutable.iis.byteiis;

import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIisFactoryFromByteIla {
    public static ByteIisFactory create(final ByteIla ila) {
        return new ByteIisFactoryImpl(ila);
    }

    private static class ByteIisFactoryImpl implements ByteIisFactory {
        private final ByteIla ila;

        public ByteIisFactoryImpl(final ByteIla ila) {
            this.ila = ila;
        }

        @Override
        public ByteIis create() {
            return ByteIisFromByteIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
