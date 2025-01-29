package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaAdd;

public class DoubleIlaFactoryAdd {
    private DoubleIlaFactoryAdd() {}

    public static DoubleIlaFactory create(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory, int bufferSize) {
        return new DoubleIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory leftFactory;
        private final DoubleIlaFactory rightFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
