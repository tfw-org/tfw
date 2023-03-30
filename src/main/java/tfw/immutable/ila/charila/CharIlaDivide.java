package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class CharIlaDivide
{
    private CharIlaDivide()
    {
    	// non-instantiable class
    }

    public static CharIla create(CharIla leftIla, CharIla rightIla)
    {
    	return create(leftIla, rightIla, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }
    
    public static CharIla create(CharIla leftIla, CharIla rightIla,
        int bufferSize)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(),
                              "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(leftIla, rightIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final CharIla leftIla;
        private final CharIla rightIla;
        private final int bufferSize;

        MyCharIla(CharIla leftIla, CharIla rightIla, int bufferSize)
        {
            super(leftIla.length());
                    
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            CharIlaIterator li = new CharIlaIterator(
                CharIlaSegment.create(leftIla, start, length), bufferSize);
            CharIlaIterator ri = new CharIlaIterator(
                CharIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride)
            {
                array[ii] = (char) (li.next() / ri.next());
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "CharIlaDivide");
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
            map.put("bufferSize", new Integer(bufferSize));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
