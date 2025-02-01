package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class LongIlaFactoryFromCastShortIlaFactory {
    private LongIlaFactoryFromCastShortIlaFactory() {}

    public static LongIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(shortIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final ShortIlaFactory shortIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final ShortIlaFactory shortIlaFactory, final int bufferSize) {
            Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

            this.shortIlaFactory = shortIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
