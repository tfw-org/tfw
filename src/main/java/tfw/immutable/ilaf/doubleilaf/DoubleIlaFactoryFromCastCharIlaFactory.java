package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastCharIla;
import tfw.immutable.ilaf.charilaf.CharIlaFactory;

public class DoubleIlaFactoryFromCastCharIlaFactory {
    private DoubleIlaFactoryFromCastCharIlaFactory() {}

    public static DoubleIlaFactory create(CharIlaFactory charIlaFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(charIlaFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final CharIlaFactory charIlaFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(final CharIlaFactory charIlaFactory, final int bufferSize) {
            Argument.assertNotNull(charIlaFactory, "charIlaFactory");

            this.charIlaFactory = charIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromCastCharIla.create(charIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
