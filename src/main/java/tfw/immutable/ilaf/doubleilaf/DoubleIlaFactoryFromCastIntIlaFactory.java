package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastIntIla;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class DoubleIlaFactoryFromCastIntIlaFactory {
    private DoubleIlaFactoryFromCastIntIlaFactory() {}

    public static DoubleIlaFactory create(IntIlaFactory intIlaFactory, final int bufferSize) {
        return new DoubleIlaFactoryImpl(intIlaFactory, bufferSize);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final IntIlaFactory intIlaFactory;
        private final int bufferSize;

        public DoubleIlaFactoryImpl(final IntIlaFactory intIlaFactory, final int bufferSize) {
            Argument.assertNotNull(intIlaFactory, "intIlaFactory");

            this.intIlaFactory = intIlaFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaFromCastIntIla.create(intIlaFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
