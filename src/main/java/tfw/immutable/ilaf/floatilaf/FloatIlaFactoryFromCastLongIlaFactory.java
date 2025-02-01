package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class FloatIlaFactoryFromCastLongIlaFactory {
    private FloatIlaFactoryFromCastLongIlaFactory() {}

    public static FloatIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
