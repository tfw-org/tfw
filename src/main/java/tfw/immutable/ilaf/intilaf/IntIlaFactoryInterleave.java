package tfw.immutable.ilaf.intilaf;

import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaInterleave;

public class IntIlaFactoryInterleave {
    private IntIlaFactoryInterleave() {}

    public static IntIlaFactory create(final IntIlaFactory[] ilaFactories, final int[] buffers) {
        return new IntIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class IntIlaFactoryImpl implements IntIlaFactory {
        private final IntIlaFactory[] ilaFactories;
        private final int[] buffers;

        public IntIlaFactoryImpl(final IntIlaFactory[] ilaFactories, final int[] buffers) {
            Argument.assertNotNull(ilaFactories, "ilaFactories");
            for (int i = 0; i < ilaFactories.length; i++) {
                Argument.assertNotNull(ilaFactories[i], "ilaFactoryies[" + i + "]");
            }
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ilaFactories.clone();
            this.buffers = buffers;
        }

        @Override
        public IntIla create() {
            final IntIla[] ilas = new IntIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return IntIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
