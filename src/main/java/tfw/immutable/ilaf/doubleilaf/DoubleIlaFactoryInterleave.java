package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class DoubleIlaFactoryInterleave {
    private DoubleIlaFactoryInterleave() {}

    public static DoubleIlaFactory create(final DoubleIlaFactory[] ilaFactories, final double[] buffers) {
        return new DoubleIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class DoubleIlaFactoryImpl implements DoubleIlaFactory {
        private final DoubleIlaFactory[] ilaFactories;
        private final double[] buffers;

        public DoubleIlaFactoryImpl(final DoubleIlaFactory[] ilaFactories, final double[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        public DoubleIla create() {
            final DoubleIla[] ilas = new DoubleIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return DoubleIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
