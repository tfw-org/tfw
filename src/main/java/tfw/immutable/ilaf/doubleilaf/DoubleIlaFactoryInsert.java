package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaInsert;

public class DoubleIlaFactoryInsert {
    private DoubleIlaFactoryInsert() {}

    public static DoubleIlaFactory create(DoubleIlaFactory ilaFactory, long index, double value) {
        return new DoubleIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory ilaFactory;
        private final long index;
        private final double value;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory ilaFactory, long index, double value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
