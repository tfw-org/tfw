package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public class FloatIlaFactorySegment {
    private FloatIlaFactorySegment() {}

    public static FloatIlaFactory create(FloatIlaFactory factory, final long start, final long length) {
        return new FloatIlaFactoryImpl(factory, start, length);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory factory;
        private final long start;
        private final long length;

        public FloatIlaFactoryImpl(final FloatIlaFactory factory, final long start, final long length) {
            Argument.assertNotNull(factory, "factory");

            this.factory = factory;
            this.start = start;
            this.length = length;
        }

        @Override
        public FloatIla create() {
            return FloatIlaSegment.create(factory.create(), start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
