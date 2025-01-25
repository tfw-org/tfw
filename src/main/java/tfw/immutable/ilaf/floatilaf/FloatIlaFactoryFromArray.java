package tfw.immutable.ilaf.floatilaf;

import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

public class FloatIlaFactoryFromArray {
    private FloatIlaFactoryFromArray() {}

    public static FloatIlaFactory create(final float[] array) {
        return new FloatIlaFactoryImpl(array);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final float[] array;

        public FloatIlaFactoryImpl(final float[] array) {
            this.array = array;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFromArray.create(array);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
