package tfw.immutable.iisf.intiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.intiis.IntIis;
import tfw.immutable.iis.intiis.IntIisFromIntIla;
import tfw.immutable.ila.intila.IntIla;

public final class IntIisFactoryFromIntIla {
    private IntIisFactoryFromIntIla() {}

    public static IntIisFactory create(final IntIla ila) {
        return new IntIisFactoryImpl(ila);
    }

    private static class IntIisFactoryImpl implements IntIisFactory {
        private final IntIla ila;

        public IntIisFactoryImpl(final IntIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public IntIis create() throws IOException {
            return IntIisFromIntIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
