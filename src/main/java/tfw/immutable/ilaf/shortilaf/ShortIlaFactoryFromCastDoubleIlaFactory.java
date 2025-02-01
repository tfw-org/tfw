package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class ShortIlaFactoryFromCastDoubleIlaFactory {
    private ShortIlaFactoryFromCastDoubleIlaFactory() {}

    public static ShortIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        return new ShortIlaFactoryImpl(doubleIlaFactory, bufferSize);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final DoubleIlaFactory doubleIlaFactory;
        private final int bufferSize;

        public ShortIlaFactoryImpl(final DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
            Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

            this.doubleIlaFactory = doubleIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public ShortIla create() {
            return ShortIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
