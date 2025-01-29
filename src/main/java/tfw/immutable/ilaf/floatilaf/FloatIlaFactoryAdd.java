package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaAdd;

public class FloatIlaFactoryAdd {
    private FloatIlaFactoryAdd() {}

    public static FloatIlaFactory create(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory, int bufferSize) {
        return new FloatIlaFactoryImpl(leftFactory, rightFactory, bufferSize);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory leftFactory;
        private final FloatIlaFactory rightFactory;
        private final int bufferSize;

        public FloatIlaFactoryImpl(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory, int bufferSize) {
            Argument.assertNotNull(leftFactory, "leftFactory");
            Argument.assertNotNull(rightFactory, "rightFactory");

            this.leftFactory = leftFactory;
            this.rightFactory = rightFactory;
            this.bufferSize = bufferSize;
        }

        @Override
        public FloatIla create() {
            return FloatIlaAdd.create(leftFactory.create(), rightFactory.create(), bufferSize);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
