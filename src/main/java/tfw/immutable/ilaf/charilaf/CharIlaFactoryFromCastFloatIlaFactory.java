package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class CharIlaFactoryFromCastFloatIlaFactory {
    private CharIlaFactoryFromCastFloatIlaFactory() {}

    public static CharIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(floatIlaFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final FloatIlaFactory floatIlaFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(final FloatIlaFactory floatIlaFactory, final int bufferSize) {
            Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

            this.floatIlaFactory = floatIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
