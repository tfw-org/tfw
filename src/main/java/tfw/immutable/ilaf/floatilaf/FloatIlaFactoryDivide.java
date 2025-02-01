package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaDivide;

public class FloatIlaFactoryDivide {
    private FloatIlaFactoryDivide() {}

    public static FloatIlaFactory create(
            final FloatIlaFactory leftFactory, final FloatIlaFactory rightFactory, final int bufferSize) {
        return new FloatIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory leftFactory;
        private final FloatIlaFactory rightFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(
                final FloatIlaFactory leftFactory, final FloatIlaFactory rightFactory, final int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaDivide.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
