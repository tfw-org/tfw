package tfw.immutable.iis.booleaniis;

import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIisFactoryFromBooleanIla {
    public static BooleanIisFactory create(final BooleanIla ila) {
        return new BooleanIisFactoryImpl(ila);
    }

    private static class BooleanIisFactoryImpl implements BooleanIisFactory {
        private final BooleanIla ila;

        public BooleanIisFactoryImpl(final BooleanIla ila) {
            this.ila = ila;
        }

        @Override
        public BooleanIis create() {
            return BooleanIisFromBooleanIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
