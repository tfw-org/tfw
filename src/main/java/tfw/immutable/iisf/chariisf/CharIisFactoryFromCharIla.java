package tfw.immutable.iisf.chariisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.chariis.CharIis;
import tfw.immutable.iis.chariis.CharIisFromCharIla;
import tfw.immutable.ila.charila.CharIla;

public final class CharIisFactoryFromCharIla {
    private CharIisFactoryFromCharIla() {}

    public static CharIisFactory create(final CharIla ila) {
        return new CharIisFactoryImpl(ila);
    }

    private static class CharIisFactoryImpl implements CharIisFactory {
        private final CharIla ila;

        public CharIisFactoryImpl(final CharIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public CharIis create() throws IOException {
            return CharIisFromCharIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
