package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaDecimate;

public class DoubleIlaFactoryDecimate {
    private DoubleIlaFactoryDecimate() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, long factor, double[] buffer) {
        return new DoubleIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory ilaFactory;
        private final long factor;
        private final double[] buffer;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory ilaFactory, long factor, double[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
