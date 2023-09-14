package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedObjectIlaFromObjectIla<T> {
    private StridedObjectIlaFromObjectIla() {}

    public static <T> StridedObjectIla<T> create(final ObjectIla<T> ila, final T[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedObjectIla<>(ila, buffer);
    }

    private static class MyStridedObjectIla<T> extends AbstractStridedObjectIla<T> {
        private final ObjectIla<T> ila;
        private final T[] buffer;

        public MyStridedObjectIla(final ObjectIla<T> ila, final T[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final T[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final ObjectIla<T> segmentObjectIla =
                    start == 0 && length == ila.length() ? ila : ObjectIlaSegment.create(ila, start, length);
            final ObjectIlaIterator<T> bii = new ObjectIlaIterator<>(segmentObjectIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
