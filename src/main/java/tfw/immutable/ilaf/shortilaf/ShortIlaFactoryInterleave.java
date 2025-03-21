package tfw.immutable.ilaf.shortilaf;

import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class ShortIlaFactoryInterleave {
    private ShortIlaFactoryInterleave() {}

    public static ShortIlaFactory create(final ShortIlaFactory[] ilaFactories, final short[] buffers) {
        return new ShortIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class ShortIlaFactoryImpl implements ShortIlaFactory {
        private final ShortIlaFactory[] ilaFactories;
        private final short[] buffers;

        public ShortIlaFactoryImpl(final ShortIlaFactory[] ilaFactories, final short[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        public ShortIla create() {
            final ShortIla[] ilas = new ShortIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return ShortIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
