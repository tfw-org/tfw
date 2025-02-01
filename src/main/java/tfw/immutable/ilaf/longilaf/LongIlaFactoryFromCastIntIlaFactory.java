package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class LongIlaFactoryFromCastIntIlaFactory {
    private LongIlaFactoryFromCastIntIlaFactory() {}

    public static LongIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
