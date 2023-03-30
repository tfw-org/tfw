package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaBound
{
    private LongIlaBound()
    {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long minimum,
        long maximum)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyLongIla(ila, minimum, maximum);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final LongIla ila;
        private final long minimum;
        private final long maximum;

        MyLongIla(LongIla ila, long minimum, long maximum)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                long tmp = array[ii];
                if (tmp < minimum)
                {
                    array[ii] = minimum;
                }
                else if (tmp > maximum)
                {
                    array[ii] = maximum;
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaBound");
            map.put("ila", getImmutableInfo(ila));
            map.put("minimum", new Long(minimum));
            map.put("maximum", new Long(maximum));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
