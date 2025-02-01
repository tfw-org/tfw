package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaDivide;

public class DoubleIlaFactoryDivide {
    private DoubleIlaFactoryDivide() {}

    public static DoubleIlaFactory create(
            final DoubleIlaFactory leftFactory, final DoubleIlaFactory rightFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory leftFactory;
        private final DoubleIlaFactory rightFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(
                final DoubleIlaFactory leftFactory, final DoubleIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
