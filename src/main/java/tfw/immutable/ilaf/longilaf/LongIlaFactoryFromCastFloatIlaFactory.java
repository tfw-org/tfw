package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class LongIlaFactoryFromCastFloatIlaFactory {
    private LongIlaFactoryFromCastFloatIlaFactory() {}

    public static LongIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(floatIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final FloatIlaFactory floatIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final FloatIlaFactory floatIlaFactory, final int bufferSize) {
            Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

            this.floatIlaFactory = floatIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
