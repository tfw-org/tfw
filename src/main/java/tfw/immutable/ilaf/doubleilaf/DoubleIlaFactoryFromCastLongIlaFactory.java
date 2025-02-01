package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class DoubleIlaFactoryFromCastLongIlaFactory {
    private DoubleIlaFactoryFromCastLongIlaFactory() {}

    public static DoubleIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
