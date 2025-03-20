package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaInsert;

public class LongIlaFactoryInsert {
    private LongIlaFactoryInsert() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, long index, long value) {
        return new LongIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory ilaFactory;
        private final long index;
        private final long value;

        public LongIlaFactoryImpl(final LongIlaFactory ilaFactory, long index, long value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public LongIla create() {
            return LongIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
