package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaInterleave;

public class LongIlaFactoryInterleave {
    private LongIlaFactoryInterleave() {}

    public static LongIlaFactory create(final LongIlaFactory[] ilaFactories, final long[] buffers) {
        return new LongIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class LongIlaFactoryImpl implements LongIlaFactory {
        private final LongIlaFactory[] ilaFactories;
        private final long[] buffers;

        public LongIlaFactoryImpl(final LongIlaFactory[] ilaFactories, final long[] buffers) {
            Argument.assertNotNull(ilaFactories, "ilaFactories");
            for (int i = 0; i < ilaFactories.length; i++) {
                Argument.assertNotNull(ilaFactories[i], "ilaFactoryies[" + i + "]");
            }
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ilaFactories.clone();
            this.buffers = buffers;
        }

        @Override
        public LongIla create() {
            final LongIla[] ilas = new LongIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return LongIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
