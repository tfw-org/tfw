package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFiltered;
import tfw.immutable.ila.floatila.FloatIlaFiltered.FloatFilter;

public class FloatIlaFactoryFiltered {
    private FloatIlaFactoryFiltered() {}

    public static FloatIlaFactory create(FloatIlaFactory ilaFactory, FloatFilter filter, float[] buffer) {
        return new FloatIlaFactoryImpl(ilaFactory, filter, buffer);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory ilaFactory;
        private final FloatFilter filter;
        private final float[] buffer;

        public FloatIlaFactoryImpl(final FloatIlaFactory ilaFactory, FloatFilter filter, float[] buffer) {
            Argument.assertNotNull(ilaFactory, "ilaFactory");

            this.ilaFactory = ilaFactory;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        public FloatIla create() {
            return FloatIlaFiltered.create(ilaFactory.create(), filter, buffer.clone());
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
