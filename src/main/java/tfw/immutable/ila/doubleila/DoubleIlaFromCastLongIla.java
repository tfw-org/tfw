package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

/**
 *
 * @immutables.types=numericnotlong
 */
public final class DoubleIlaFromCastLongIla
{
    private DoubleIlaFromCastLongIla()
    {
    	// non-instantiable class
    }

    public static DoubleIla create(LongIla longIla)
    {
        return create(longIla, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(LongIla longIla, int bufferSize)
    {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(longIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
        implements ImmutableProxy
    {
        private final LongIla longIla;
        private final int bufferSize;

        MyDoubleIla(LongIla longIla, int bufferSize)
        {
            super(longIla.length());
                    
            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            LongIlaIterator fi = new LongIlaIterator(
                LongIlaSegment.create(longIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (double) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "DoubleIlaFromCastLongIla");
            map.put("longIla", getImmutableInfo(longIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
