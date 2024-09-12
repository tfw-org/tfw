package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlmFromStridedObjectIlm<T> {
    private ObjectIlmFromStridedObjectIlm() {}

    public static <T> ObjectIlm<T> create(final StridedObjectIlm<T> stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new ObjectIlmImpl<>(stridedIlm);
    }

    private static class ObjectIlmImpl<T> extends AbstractObjectIlm<T> {
        private final StridedObjectIlm<T> stridedIlm;

        private ObjectIlmImpl(final StridedObjectIlm<T> stridedIlm) {
            this.stridedIlm = stridedIlm;
        }

        @Override
        protected long widthImpl() throws IOException {
            return stridedIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return stridedIlm.height();
        }

        @Override
        protected void getImpl(
                final T[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
