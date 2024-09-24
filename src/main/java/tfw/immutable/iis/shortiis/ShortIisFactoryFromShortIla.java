package tfw.immutable.iis.shortiis;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIisFactoryFromShortIla {
    private ShortIisFactoryFromShortIla() {}

    public static ShortIisFactory create(final ShortIla ila) {
        return new ShortIisFactoryImpl(ila);
    }

    private static class ShortIisFactoryImpl implements ShortIisFactory {
        private final ShortIla ila;

        public ShortIisFactoryImpl(final ShortIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public ShortIis create() {
            return ShortIisFromShortIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
