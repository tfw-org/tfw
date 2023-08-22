package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlmFromStridedObjectIlm<T> {
    private ObjectIlmFromStridedObjectIlm() {}

    public static <T> ObjectIlm<T> create(final StridedObjectIlm<T> stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyObjectIlm<>(stridedIlm);
    }

    private static class MyObjectIlm<T> extends AbstractObjectIlm<T> {
        private final StridedObjectIlm<T> stridedIlm;

        public MyObjectIlm(final StridedObjectIlm<T> stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final T[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
