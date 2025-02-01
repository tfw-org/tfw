package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class FloatIlaFactoryFromCastIntIlaFactory {
    private FloatIlaFactoryFromCastIntIlaFactory() {}

    public static FloatIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
