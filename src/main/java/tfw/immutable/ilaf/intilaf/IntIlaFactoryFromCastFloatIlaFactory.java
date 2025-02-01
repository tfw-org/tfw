package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class IntIlaFactoryFromCastFloatIlaFactory {
    private IntIlaFactoryFromCastFloatIlaFactory() {}

    public static IntIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        return new IntIlaFactoryImpl(floatIlaFactory, bufferSize);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final FloatIlaFactory floatIlaFactory;
        private final int bufferSize;

        public IntIlaFactoryImpl(final FloatIlaFactory floatIlaFactory, final int bufferSize) {
            Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

            this.floatIlaFactory = floatIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public IntIla create() {
            return IntIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
