package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public class DoubleIlaFactorySegment {
    private DoubleIlaFactorySegment() {}

    public static DoubleIlaFactory create(DoubleIlaFactory factory, final long start, final long length) {
        return new DoubleIlaFactoryImpl(factory, start, length);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory factory;
        private final long start;
        private final long length;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public DoubleIla create() {
            return DoubleIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
