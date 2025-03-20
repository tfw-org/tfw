package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaInvert;

public class FloatIlaFactoryInvert {
    private FloatIlaFactoryInvert() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory) {
        return new FloatIlaFactoryImpl(ilaFactory);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory ilaFactory;

        public FloatIlaFactoryImpl(final FloatIlaFactory ilaFactory) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
        }

        @Override
        public FloatIla create() {
            return FloatIlaInvert.create(ilaFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
