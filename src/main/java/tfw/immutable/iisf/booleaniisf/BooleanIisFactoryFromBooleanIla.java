package tfw.immutable.iisf.booleaniisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.booleaniis.BooleanIis;
import tfw.immutable.iis.booleaniis.BooleanIisFromBooleanIla;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIisFactoryFromBooleanIla {
    private BooleanIisFactoryFromBooleanIla() {}

    public static BooleanIisFactory create(final BooleanIla ila) {
        return new BooleanIisFactoryImpl(ila);
    }

    private static class BooleanIisFactoryImpl implements BooleanIisFactory {
        private final BooleanIla ila;

        public BooleanIisFactoryImpl(final BooleanIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public BooleanIis create() throws IOException {
            return BooleanIisFromBooleanIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
