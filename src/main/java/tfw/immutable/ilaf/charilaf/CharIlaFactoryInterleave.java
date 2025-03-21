package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaInterleave;
import tfw.immutable.ilaf.ImmutableLongArrayFactoryUtil;

public class CharIlaFactoryInterleave {
    private CharIlaFactoryInterleave() {}

    public static CharIlaFactory create(final CharIlaFactory[] ilaFactories, final char[] buffers) {
        return new CharIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory[] ilaFactories;
        private final char[] buffers;

        public CharIlaFactoryImpl(final CharIlaFactory[] ilaFactories, final char[] buffers) {
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ImmutableLongArrayFactoryUtil.assertNotNullAndClone(ilaFactories, "ilaFactories");
            this.buffers = buffers;
        }

        @Override
        public CharIla create() {
            final CharIla[] ilas = new CharIla[ilaFactories.length];

            for (int i = 0; i < ilas.length; i++) {
                ilas[i] = ilaFactories[i].create();
            }

            return CharIlaInterleave.create(ilas, buffers);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
