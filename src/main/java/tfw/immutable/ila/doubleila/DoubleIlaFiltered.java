package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaFiltered
{
    private DoubleIlaFiltered()
    {
        // non-instantiable class
    }

    public static interface DoubleFilter {
        public boolean matches(double value);
    }

    public static DoubleIla create(DoubleIla ila, DoubleFilter filter)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyDoubleIla(ila, filter);
    }

    private static class MyDoubleIla implements DoubleIla,
        ImmutableLongArray, ImmutableProxy
    {
        private final DoubleIla ila;
        private final DoubleFilter filter;

        private long length = -1;

        private MyDoubleIla(DoubleIla ila, DoubleFilter filter)
        {
            this.ila = ila;
            this.filter = filter;
        }
        
        public final long length() {
            calculateLength();

            return length;
        }

        public final double[] toArray()
            throws DataInvalidException
        {
            calculateLength();

            if(length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException
                    ("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final double[] toArray(long start, int length)
            throws DataInvalidException
        {
            calculateLength();

            double[] result = new double[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(double[] array, int offset,
                                  long start, int length)
            throws DataInvalidException
        {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(double[] array, int offset, int stride,
                                  long start, int length)
            throws DataInvalidException
        {
            calculateLength();

            if(length == 0)
            {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            DoubleIlaIterator oii = new DoubleIlaIterator(DoubleIlaSegment.create(ila, start));
            
            // left off here
            for (int i=offset; oii.hasNext(); i+=stride) {
                double node = oii.next();
                
                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength()
        {
            if (length < 0) {			
                length = ila.length();
                DoubleIlaIterator oii = new DoubleIlaIterator(ila);
                
                try {
                    for (int i=0 ; oii.hasNext() ; i++) {
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

        public Map<String, Object> getParameters()
        {
            calculateLength();

            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "DoubleIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
