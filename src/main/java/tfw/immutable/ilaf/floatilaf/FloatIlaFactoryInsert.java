package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaInsert;

public class FloatIlaFactoryInsert {
    private FloatIlaFactoryInsert() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, long index, float value) {
        return new FloatIlaFactoryImpl(ilaFactory, index, value);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory ilaFactory;
        private final long index;
        private final float value;

        public FloatIlaFactoryImpl(final FloatIlaFactory ilaFactory, long index, float value) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.index = index;
            this.value = value;
        }

        @Override
        public FloatIla create() {
            return FloatIlaInsert.create(ilaFactory.create(), index, value);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
