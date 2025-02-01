package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class ShortIlaFactoryFromCastFloatIlaFactory {
    private ShortIlaFactoryFromCastFloatIlaFactory() {}

    public static ShortIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(floatIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final FloatIlaFactory floatIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final FloatIlaFactory floatIlaFactory, final int bufferSize) {
            Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

            this.floatIlaFactory = floatIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
