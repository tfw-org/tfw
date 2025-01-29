package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFiltered;
import tfw.immutable.ila.longila.LongIlaFiltered.LongFilter;

public class LongIlaFactoryFiltered {
    private LongIlaFactoryFiltered() {}

    public static LongIlaFactory create(LongIlaFactory ilaFactory, LongFilter filter, long[] buffer) {
        return new LongIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory ilaFactory;
        private final LongFilter filter;
        private final long[] buffer;

        public LongIlaFactoryImpl(final LongIlaFactory ilaFactory, LongFilter filter, long[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public LongIla create() {
            return LongIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
