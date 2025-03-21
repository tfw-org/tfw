package tfw.immutable.ilaf.byteilaf;

import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class ByteIlaFactoryInterleave {
    private ByteIlaFactoryInterleave() {}

    public static ByteIlaFactory create(final ByteIlaFactory[] ilaFactories, final byte[] buffers) {
        return new ByteIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class ByteIlaFactoryImpl implements ByteIlaFactory {
        private final ByteIlaFactory[] ilaFactories;
        private final byte[] buffers;

        public ByteIlaFactoryImpl(final ByteIlaFactory[] ilaFactories, final byte[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        public ByteIla create() {
            final ByteIla[] ilas = new ByteIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return ByteIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
