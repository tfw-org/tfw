package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaSegment;

public class LongIlaFactorySegment {
    private LongIlaFactorySegment() {}

    public static LongIlaFactory create(LongIlaFactory factory, final long start, final long length) {
        return new LongIlaFactoryImpl(factory, start, length);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory factory;
        private final long start;
        private final long length;

        public LongIlaFactoryImpl(final LongIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public LongIla create() {
            return LongIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
