package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaDecimate;

public class LongIlaFactoryDecimate {
    private LongIlaFactoryDecimate() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long factor, long[] buffer) {
        return new LongIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory ilaFactory;
        private final long factor;
        private final long[] buffer;

        public LongIlaFactoryImpl(final LongIlaFactory ilaFactory, long factor, long[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public LongIla create() {
            return LongIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
