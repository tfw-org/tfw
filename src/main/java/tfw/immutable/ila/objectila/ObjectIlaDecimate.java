package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlaDecimate {
    private ObjectIlaDecimate() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long factor, T[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyObjectIla<>(ila, factor, buffer);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final long factor;
        private final T[] buffer;

        MyObjectIla(ObjectIla<T> ila, long factor, T[] buffer) {
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void toArrayImpl(T[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ObjectIla<T> segment = ObjectIlaSegment.create(ila, segmentStart, segmentLength);
            final ObjectIlaIterator<T> fi = new ObjectIlaIterator<T>(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
