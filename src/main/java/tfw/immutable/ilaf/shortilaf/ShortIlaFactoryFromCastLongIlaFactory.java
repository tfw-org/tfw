package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class ShortIlaFactoryFromCastLongIlaFactory {
    private ShortIlaFactoryFromCastLongIlaFactory() {}

    public static ShortIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
