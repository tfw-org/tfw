package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaBound;

public class FloatIlaFactoryBound {
    private FloatIlaFactoryBound() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, float minimum, float maximum) {
        return new FloatIlaFactoryImpl(ilaFactory, minimum, maximum);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory ilaFactory;
        private final float minimum;
        private final float maximum;

        public FloatIlaFactoryImpl(final FloatIlaFactory ilaFactory, float minimum, float maximum) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public FloatIla create() {
            return FloatIlaBound.create(ilaFactory.create(), minimum, maximum);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
