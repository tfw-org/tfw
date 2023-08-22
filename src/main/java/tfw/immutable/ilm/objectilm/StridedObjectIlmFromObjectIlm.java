package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedObjectIlmFromObjectIlm<T> {
    private StridedObjectIlmFromObjectIlm() {}

    public static <T> StridedObjectIlm<T> create(final ObjectIlm<T> ilm, final T[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedObjectIlm<>(ilm, buffer);
    }

    private static class MyStridedObjectIlm<T> extends AbstractStridedObjectIlm<T> {
        private final ObjectIlm<T> ilm;
        private final T[] buffer;

        public MyStridedObjectIlm(final ObjectIlm<T> ilm, final T[] buffer) {
            super(ilm.width(), ilm.height());

            this.ilm = ilm;
            this.buffer = buffer;
        }

        public final void toArrayImpl(
                T[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final T[] b = Arrays.copyOf(buffer, colCount);

            for (int i = 0; i < rowCount; i++) {
                ilm.toArray(b, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] = b[j];
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
