package tfw.immutable.iisf.longiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.longiis.LongIis;
import tfw.immutable.iis.longiis.LongIisFromLongIla;
import tfw.immutable.ila.longila.LongIla;

public final class LongIisFactoryFromLongIla {
    private LongIisFactoryFromLongIla() {}

    public static LongIisFactory create(final LongIla ila) {
        return new LongIisFactoryImpl(ila);
    }

    private static class LongIisFactoryImpl implements LongIisFactory {
        private final LongIla ila;

        public LongIisFactoryImpl(final LongIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public LongIis create() throws IOException {
            return LongIisFromLongIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
