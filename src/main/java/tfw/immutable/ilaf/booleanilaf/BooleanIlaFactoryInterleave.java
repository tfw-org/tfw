package tfw.immutable.ilaf.booleanilaf;

import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class BooleanIlaFactoryInterleave {
    private BooleanIlaFactoryInterleave() {}

    public static BooleanIlaFactory create(final BooleanIlaFactory[] ilaFactories, final boolean[] buffers) {
        return new BooleanIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class BooleanIlaFactoryImpl implements BooleanIlaFactory {
        private final BooleanIlaFactory[] ilaFactories;
        private final boolean[] buffers;

        public BooleanIlaFactoryImpl(final BooleanIlaFactory[] ilaFactories, final boolean[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        public BooleanIla create() {
            final BooleanIla[] ilas = new BooleanIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return BooleanIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
