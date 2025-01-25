package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaConcatenate;

public class DoubleIlaFactoryConcatenate {
    private DoubleIlaFactoryConcatenate() {}

    public static DoubleIlaFactory create(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory) {
        return new DoubleIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory leftFactory;
        private final DoubleIlaFactory rightFactory;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
