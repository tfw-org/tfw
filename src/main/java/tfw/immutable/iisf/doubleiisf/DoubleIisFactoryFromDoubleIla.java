package tfw.immutable.iisf.doubleiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.doubleiis.DoubleIis;
import tfw.immutable.iis.doubleiis.DoubleIisFromDoubleIla;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIisFactoryFromDoubleIla {
    private DoubleIisFactoryFromDoubleIla() {}

    public static DoubleIisFactory create(final DoubleIla ila) {
        return new DoubleIisFactoryImpl(ila);
    }

    private static class DoubleIisFactoryImpl implements DoubleIisFactory {
        private final DoubleIla ila;

        public DoubleIisFactoryImpl(final DoubleIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public DoubleIis create() throws IOException {
            return DoubleIisFromDoubleIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
