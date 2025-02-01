package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class FloatIlaFactoryFromCastDoubleIlaFactory {
    private FloatIlaFactoryFromCastDoubleIlaFactory() {}

    public static FloatIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
