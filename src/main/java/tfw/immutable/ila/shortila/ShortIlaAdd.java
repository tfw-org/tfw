package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class ShortIlaAdd
{
    private ShortIlaAdd()
    {
    	// non-instantiable class
    }

    public static ShortIla create(ShortIla leftIla, ShortIla rightIla)
    {
    	return create(leftIla, rightIla, ShortIlaIterator.DEFAULT_BUFFER_SIZE);
    }
    
    public static ShortIla create(ShortIla leftIla, ShortIla rightIla,
        int bufferSize)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(),
                              "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(leftIla, rightIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final ShortIla leftIla;
        private final ShortIla rightIla;
        private final int bufferSize;

        MyShortIla(ShortIla leftIla, ShortIla rightIla, int bufferSize)
        {
            super(leftIla.length());
                    
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ShortIlaIterator li = new ShortIlaIterator(
                ShortIlaSegment.create(leftIla, start, length), bufferSize);
            ShortIlaIterator ri = new ShortIlaIterator(
                ShortIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (short) (li.next() + ri.next());
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaAdd");
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
            map.put("bufferSize", new Integer(bufferSize));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
