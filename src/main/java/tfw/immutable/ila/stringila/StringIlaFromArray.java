package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaFromArray
{
    private StringIlaFromArray()
    {
        // non-instantiable class
    }

    public static StringIla create(String[] array)
    {
        return create(array, true);
    }

    public static StringIla create(String[] array, boolean cloneArray)
    {
        Argument.assertNotNull(array, "array");

        return new MyStringIla(array, cloneArray);
    }

    private static class MyStringIla extends AbstractStringIla
        implements ImmutableProxy
    {
        private final String[] array;

        MyStringIla(String[] array, boolean cloneArray)
        {
            super(array.length);

            if (cloneArray)
            {
                this.array = (String[])array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(String[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = this.array[startInt];
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "StringIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
