package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class FloatIlaFactoryFromCastShortIlaFactory {
    private FloatIlaFactoryFromCastShortIlaFactory() {}

    public static FloatIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(shortIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final ShortIlaFactory shortIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final ShortIlaFactory shortIlaFactory, final int bufferSize) {
            Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

            this.shortIlaFactory = shortIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
