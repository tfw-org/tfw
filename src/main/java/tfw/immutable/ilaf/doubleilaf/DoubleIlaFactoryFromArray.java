package tfw.immutable.ilaf.doubleilaf;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class DoubleIlaFactoryFromArray {
    private DoubleIlaFactoryFromArray() {}

    public static DoubleIlaFactory create(final double[] array) {
        return new DoubleIlaFactoryImpl(array);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final double[] array;

        public DoubleIlaFactoryImpl(final double[] array) {
            this.array = array;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
