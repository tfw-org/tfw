package tfw.immutable.iisf.shortiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.shortiis.ShortIis;
import tfw.immutable.iis.shortiis.ShortIisFromShortIla;
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
        public ShortIis create() throws IOException {
            return ShortIisFromShortIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
