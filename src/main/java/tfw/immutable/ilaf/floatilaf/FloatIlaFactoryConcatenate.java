package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaConcatenate;

public class FloatIlaFactoryConcatenate {
    private FloatIlaFactoryConcatenate() {}

    public static FloatIlaFactory create(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory) {
        return new FloatIlaFactoryImpl(leftFactory, rightFactory);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory leftFactory;
        private final FloatIlaFactory rightFactory;

        public FloatIlaFactoryImpl(final FloatIlaFactory leftFactory, FloatIlaFactory rightFactory) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
        }

        @Override
        public FloatIla create() {
            return FloatIlaConcatenate.create(leftFactory.create(), rightFactory.create());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
