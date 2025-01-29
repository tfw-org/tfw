package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaBound;

public class LongIlaFactoryBound {
    private LongIlaFactoryBound() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long minimum, long maximum) {
        return new LongIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory ilaFactory;
        private final long minimum;
        private final long maximum;

        public LongIlaFactoryImpl(final LongIlaFactory ilaFactory, long minimum, long maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public LongIla create() {
            return LongIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
