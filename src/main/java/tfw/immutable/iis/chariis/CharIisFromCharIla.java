package tfw.immutable.iis.chariis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;

public final class CharIisFromCharIla {
    private CharIisFromCharIla() {}

    public static CharIis create(final CharIla ila) {
        return new CharIisImpl(ila);
    }

    private static class CharIisImpl extends AbstractCharIis {
        private final CharIla ila;

        private long index = 0;

        public CharIisImpl(final CharIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(char[] array, int offset, int length) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            index += elementsToGet;

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
