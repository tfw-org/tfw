package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class CharIlaFactoryFromCastDoubleIlaFactory {
    private CharIlaFactoryFromCastDoubleIlaFactory() {}

    public static CharIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new CharIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public CharIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public CharIla create() {
            return CharIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
