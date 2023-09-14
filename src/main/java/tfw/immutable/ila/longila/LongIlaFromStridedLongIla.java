package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaFromStridedLongIla {
    private LongIlaFromStridedLongIla() {}

    public static LongIla create(final StridedLongIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new MyLongIla(stridedIla);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final StridedLongIla stridedIla;

        public MyLongIla(final StridedLongIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final long[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
