package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaBound;

public class DoubleIlaFactoryBound {
    private DoubleIlaFactoryBound() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, double minimum, double maximum) {
        return new DoubleIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory ilaFactory;
        private final double minimum;
        private final double maximum;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory ilaFactory, double minimum, double maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
