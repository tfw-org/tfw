package tfw.immutable.ilaf.charilaf;

import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaInterleave;

public class CharIlaFactoryInterleave {
    private CharIlaFactoryInterleave() {}

    public static CharIlaFactory create(final CharIlaFactory[] ilaFactories, final char[] buffers) {
        return new CharIlaFactoryImpl(ilaFactories, buffers);
    }

    private static class CharIlaFactoryImpl implements CharIlaFactory {
        private final CharIlaFactory[] ilaFactories;
        private final char[] buffers;

        public CharIlaFactoryImpl(final CharIlaFactory[] ilaFactories, final char[] buffers) {
            Argument.assertNotNull(ilaFactories, "ilaFactories");
            for (int i = 0; i < ilaFactories.length; i++) {
                Argument.assertNotNull(ilaFactories[i], "ilaFactoryies[" + i + "]");
            }
            Argument.assertNotNull(buffers, "buffers");

            this.ilaFactories = ilaFactories.clone();
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
