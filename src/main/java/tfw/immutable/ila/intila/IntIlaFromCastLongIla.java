package tfw.immutable.ila.intila;

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
public final class IntIlaFromCastLongIla
{
    private IntIlaFromCastLongIla()
    {
    	// non-instantiable class
    }

    public static IntIla create(LongIla longIla)
    {
        return create(longIla, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(LongIla longIla, int bufferSize)
    {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(longIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla
        implements ImmutableProxy
    {
        private final LongIla longIla;
        private final int bufferSize;

        MyIntIla(LongIla longIla, int bufferSize)
        {
            super(longIla.length());
                    
            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            LongIlaIterator fi = new LongIlaIterator(
                LongIlaSegment.create(longIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (int) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "IntIlaFromCastLongIla");
            map.put("longIla", getImmutableInfo(longIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
