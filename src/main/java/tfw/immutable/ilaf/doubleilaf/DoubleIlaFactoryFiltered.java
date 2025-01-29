package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFiltered;
import tfw.immutable.ila.doubleila.DoubleIlaFiltered.DoubleFilter;

public class DoubleIlaFactoryFiltered {
    private DoubleIlaFactoryFiltered() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, DoubleFilter filter, double[] buffer) {
        return new DoubleIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory ilaFactory;
        private final DoubleFilter filter;
        private final double[] buffer;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory ilaFactory, DoubleFilter filter, double[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
