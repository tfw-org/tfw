package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFiltered
{
    private ObjectIlaFiltered() {}

    public interface ObjectFilter<T> {
        boolean matches(T value);
    }

    public static <T> ObjectIla<T> create(final ObjectIla<T> ila, final ObjectFilter<T> filter, final T[] buffer)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyObjectIla<>(ila, filter, buffer);
    }

    private static class MyObjectIla<T> implements ObjectIla<T>
    {
        private final ObjectIla<T> ila;
        private final ObjectFilter<T> filter;
        private final T[] buffer;

        private long length = -1;

        private MyObjectIla(final ObjectIla<T> ila, final ObjectFilter<T> filter, final T[] buffer)
        {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }
        
        @Override
        public final long length() {
            calculateLength();

            return length;
        }

        @Override
        public final void toArray(final Object[] array, final int offset,
                                  final long start, final int length)
            throws DataInvalidException
        {
            toArray(array, offset, 1, start, length);
        }

        @Override
        public final void toArray(final Object[] array, final int offset, final int stride,
                                  final long start, final int length)
            throws DataInvalidException
        {
            calculateLength();

            if(length == 0)
            {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            final ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ObjectIlaSegment.create(ila, start, ila.length()-start), buffer);
            
            // left off here
            for (int i=offset; oii.hasNext(); i+=stride) {
                final T node = oii.next();
                
                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength()
        {
            if (length < 0) {			
                length = ila.length();
                final ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ila, buffer);
                
                try {
                    while (oii.hasNext()) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                }
                catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
