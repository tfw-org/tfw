package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class ShortIlaFactoryFromCastCharIlaFactory {
    private ShortIlaFactoryFromCastCharIlaFactory() {}

    public static ShortIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
