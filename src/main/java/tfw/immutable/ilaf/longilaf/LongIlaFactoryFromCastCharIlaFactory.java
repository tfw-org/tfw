package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class LongIlaFactoryFromCastCharIlaFactory {
    private LongIlaFactoryFromCastCharIlaFactory() {}

    public static LongIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new LongIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public LongIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public LongIla create() {
            return LongIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
