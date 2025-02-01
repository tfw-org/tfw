package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class CharIlaFactoryFromCastLongIlaFactory {
    private CharIlaFactoryFromCastLongIlaFactory() {}

    public static CharIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(longIlaFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final LongIlaFactory longIlaFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(final LongIlaFactory longIlaFactory, final int bufferSize) {
            Argument.assertNotNull(longIlaFactory, "longIlaFactory");

            this.longIlaFactory = longIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
