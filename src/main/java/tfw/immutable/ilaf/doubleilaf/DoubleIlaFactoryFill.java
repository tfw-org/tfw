package tfw.immutable.ilaf.doubleilaf;

import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFill;

public class DoubleIlaFactoryFill {
    private DoubleIlaFactoryFill() {}

    public static DoubleIlaFactory create(final double value, final long length) {
        return new DoubleIlaFactoryImpl(value, length);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final double value;
        private final long length;

        public DoubleIlaFactoryImpl(final double value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
