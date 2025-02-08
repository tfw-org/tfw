package tfw.immutable.iisf.byteiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.byteiis.ByteIis;
import tfw.immutable.iis.byteiis.ByteIisFromByteIla;
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
        public ByteIis create() throws IOException {
            return ByteIisFromByteIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
