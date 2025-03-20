package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaInvert;

public class DoubleIlaFactoryInvert {
    private DoubleIlaFactoryInvert() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory) {
        return new DoubleIlaFactoryImpl(ilaFactory);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory ilaFactory;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory ilaFactory) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaInvert.create(ilaFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
