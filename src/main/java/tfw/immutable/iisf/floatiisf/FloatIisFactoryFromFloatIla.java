package tfw.immutable.iisf.floatiisf;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.floatiis.FloatIis;
import tfw.immutable.iis.floatiis.FloatIisFromFloatIla;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIisFactoryFromFloatIla {
    private FloatIisFactoryFromFloatIla() {}

    public static FloatIisFactory create(final FloatIla ila) {
        return new FloatIisFactoryImpl(ila);
    }

    private static class FloatIisFactoryImpl implements FloatIisFactory {
        private final FloatIla ila;

        public FloatIisFactoryImpl(final FloatIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public FloatIis create() throws IOException {
            return FloatIisFromFloatIla.create(ila);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
