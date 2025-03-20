package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaInterleave;

public class FloatIlaFactoryInterleave {
    private FloatIlaFactoryInterleave() {}

    public static FloatIlaFactory create(final FloatIlaFactory[] ilaFactories, final float[] buffers) {
        return new FloatIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class FloatIlaFactoryImpl implements FloatIlaFactory {
        private final FloatIlaFactory[] ilaFactories;
        private final float[] buffers;

        public FloatIlaFactoryImpl(final FloatIlaFactory[] ilaFactories, final float[] buffers) {
            Argument.assertNotNull(ilaFactories, "ilaFactories");
            for (int i = 0; i < ilaFactories.length; i++) {
                Argument.assertNotNull(ilaFactories[i], "ilaFactoryies[" + i + "]");
            }
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ilaFactories.clone();
            this.buffers = buffers;
        }

        @Override
        public FloatIla create() {
            final FloatIla[] ilas = new FloatIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return FloatIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
