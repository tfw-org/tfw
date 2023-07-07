package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaDecimate {
    private ObjectIlaDecimate() {
        // non-instantiable class
    }

    public static ObjectIla create(ObjectIla ila, long factor) {
        return create(ila, factor, ObjectIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ObjectIla create(ObjectIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyObjectIla(ila, factor, bufferSize);
    }

    private static class MyObjectIla extends AbstractObjectIla {
        private final ObjectIla ila;
        private final long factor;
        private final int bufferSize;

        MyObjectIla(ObjectIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(Object[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ObjectIla segment = ObjectIlaSegment.create(ila, segmentStart, segmentLength);
            final ObjectIlaIterator fi = new ObjectIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
