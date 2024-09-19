package tfw.immutable.iis.doubleiis;

import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIisFactoryFromDoubleIla {
    private DoubleIisFactoryFromDoubleIla() {}

    public static DoubleIisFactory create(final DoubleIla ila) {
        return new DoubleIisFactoryImpl(ila);
    }

    private static class DoubleIisFactoryImpl implements DoubleIisFactory {
        private final DoubleIla ila;

        public DoubleIisFactoryImpl(final DoubleIla ila) {
            this.ila = ila;
        }

        @Override
        public DoubleIis create() {
            return DoubleIisFromDoubleIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
