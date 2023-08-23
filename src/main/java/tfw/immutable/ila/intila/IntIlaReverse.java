package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaReverse {
    private IntIlaReverse() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, final int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyIntIla(ila, buffer);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final int[] buffer;

        MyIntIla(IntIla ila, final int[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long start, int length) throws IOException {
            final StridedIntIla stridedIntIla = new StridedIntIla(ila, buffer.clone());

            stridedIntIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
