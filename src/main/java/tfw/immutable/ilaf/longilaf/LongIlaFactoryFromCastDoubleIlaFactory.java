package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class LongIlaFactoryFromCastDoubleIlaFactory {
    private LongIlaFactoryFromCastDoubleIlaFactory() {}

    public static LongIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
