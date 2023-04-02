package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaDecimate
{
    private ByteIlaDecimate()
    {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long factor)
    {
        return create(ila, factor, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(ByteIla ila, long factor, int bufferSize)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(ila, factor, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final ByteIla ila;
        private final long factor;
        private final int bufferSize;

        MyByteIla(ByteIla ila, long factor, int bufferSize)
        {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() -
                segmentStart, length * factor - 1);
            ByteIlaIterator fi = new ByteIlaIterator(
                ByteIlaSegment.create(ila, segmentStart, segmentLength),
                    bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (byte) fi.next();
                fi.skip(factor - 1);
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ByteIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE