package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaFromArray
{
    private ShortIlaFromArray()
    {
        // non-instantiable class
    }

    public static ShortIla create(short[] array)
    {
        return create(array, true);
    }

    public static ShortIla create(short[] array, boolean cloneArray)
    {
        Argument.assertNotNull(array, "array");

        return new MyShortIla(array, cloneArray);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final short[] array;

        MyShortIla(short[] array, boolean cloneArray)
        {
            super(array.length);

            if (cloneArray)
            {
                this.array = (short[])array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(short[] array, int offset,
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
                        
            map.put("name", "ShortIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
