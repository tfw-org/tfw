package tfw.immutable.ilaf.floatilaf;

import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFill;

public class FloatIlaFactoryFill {
    private FloatIlaFactoryFill() {}

    public static FloatIlaFactory create(final float value, final long length) {
        return new FloatIlaFactoryImpl(value, length);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final float value;
        private final long length;

        public FloatIlaFactoryImpl(final float value, final long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFill.create(value, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
