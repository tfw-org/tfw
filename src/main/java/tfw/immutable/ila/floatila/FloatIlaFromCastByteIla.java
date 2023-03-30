package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

/**
 *
 * @immutables.types=numericnotbyte
 */
public final class FloatIlaFromCastByteIla
{
    private FloatIlaFromCastByteIla()
    {
    	// non-instantiable class
    }

    public static FloatIla create(ByteIla byteIla)
    {
        return create(byteIla, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(ByteIla byteIla, int bufferSize)
    {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(byteIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyFloatIla(ByteIla byteIla, int bufferSize)
        {
            super(byteIla.length());
                    
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ByteIlaIterator fi = new ByteIlaIterator(
                ByteIlaSegment.create(byteIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (float) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaFromCastByteIla");
            map.put("byteIla", getImmutableInfo(byteIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
