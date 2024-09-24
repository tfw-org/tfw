package tfw.immutable.iis.intiis;

import tfw.check.Argument;
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
        public IntIis create() {
            return IntIisFromIntIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
