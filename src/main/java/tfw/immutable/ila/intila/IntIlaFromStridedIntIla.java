package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaFromStridedIntIla {
    private IntIlaFromStridedIntIla() {}

    public static IntIla create(final StridedIntIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new MyIntIla(stridedIla);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final StridedIntIla stridedIla;

        public MyIntIla(final StridedIntIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final int[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
