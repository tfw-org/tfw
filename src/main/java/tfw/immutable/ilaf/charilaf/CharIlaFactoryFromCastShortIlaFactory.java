package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromCastShortIla;
import tfw.immutable.ilaf.shortilaf.ShortIlaFactory;

public class CharIlaFactoryFromCastShortIlaFactory {
    private CharIlaFactoryFromCastShortIlaFactory() {}

    public static CharIlaFactory create(ShortIlaFactory shortIlaFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(shortIlaFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final ShortIlaFactory shortIlaFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(final ShortIlaFactory shortIlaFactory, final int bufferSize) {
            Argument.assertNotNull(shortIlaFactory, "shortIlaFactory");

            this.shortIlaFactory = shortIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaFromCastShortIla.create(shortIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
