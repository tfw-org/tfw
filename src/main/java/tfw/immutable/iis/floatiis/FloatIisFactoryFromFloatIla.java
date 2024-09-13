package tfw.immutable.iis.floatiis;

import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIisFactoryFromFloatIla {
    public static FloatIisFactory create(final FloatIla ila) {
        return new FloatIisFactoryImpl(ila);
    }

    private static class FloatIisFactoryImpl implements FloatIisFactory {
        private final FloatIla ila;

        public FloatIisFactoryImpl(final FloatIla ila) {
            this.ila = ila;
        }

        @Override
        public FloatIis create() {
            return FloatIisFromFloatIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
