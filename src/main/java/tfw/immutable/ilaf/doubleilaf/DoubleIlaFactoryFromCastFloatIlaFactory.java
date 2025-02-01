package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class DoubleIlaFactoryFromCastFloatIlaFactory {
    private DoubleIlaFactoryFromCastFloatIlaFactory() {}

    public static DoubleIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(floatIlaFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final FloatIlaFactory floatIlaFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(final FloatIlaFactory floatIlaFactory, final int bufferSize) {
            Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

            this.floatIlaFactory = floatIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
