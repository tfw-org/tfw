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
public final class LongIlaNegate
{
    private LongIlaNegate()
    {
    	// non-instantiable class
    }

    public static LongIla create(LongIla ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final LongIla ila;

        MyLongIla(LongIla ila)
        {
            super(ila.length());
                    
            this.ila = ila;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (long) -array[ii];
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaNegate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
