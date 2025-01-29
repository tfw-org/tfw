package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaDecimate;

public class FloatIlaFactoryDecimate {
    private FloatIlaFactoryDecimate() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, long factor, float[] buffer) {
        return new FloatIlaFactoryImpl(ilaFactory, factor, buffer);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory ilaFactory;
        private final long factor;
        private final float[] buffer;

        public FloatIlaFactoryImpl(final FloatIlaFactory ilaFactory, long factor, float[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        public FloatIla create() {
            return FloatIlaDecimate.create(ilaFactory.create(), factor, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
