package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaFromArray {
    private ShortIlaFromArray() {
        // non-instantiable class
    }

    /**
     * Creates an immutable array backed by the specified array.
     *
     * <p>The supplied array is not copied. Ownership of the array is transferred
     * to the returned ILA, allowing the ILA to use the array directly without
     * the overhead of copying it. The caller must not access or modify the
     * array after this method returns.
     *
     * <p>If the caller needs to retain and use the original array, a clone
     * should be passed instead.
     *
     * @param array the array whose ownership is transferred to the returned ILA
     * @return an ILA backed by the supplied array
     */
    public static ShortIla create(short[] array) {
        Argument.assertNotNull(array, "array");

        return new ShortIlaImpl(array);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final short[] array;

        private ShortIlaImpl(short[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }

        @Override
        protected void closeImpl() throws IOException {
            // Nothing to do.
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
