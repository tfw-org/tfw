package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaFromArray
{
    private IntIlaFromArray()
    {
        // non-instantiable class
    }

    public static IntIla create(int[] array)
    {
        return create(array, true);
    }

    public static IntIla create(int[] array, boolean cloneArray)
    {
        Argument.assertNotNull(array, "array");

        return new MyIntIla(array, cloneArray);
    }

    private static class MyIntIla extends AbstractIntIla
        implements ImmutableProxy
    {
        private final int[] array;

        MyIntIla(int[] array, boolean cloneArray)
        {
            super(array.length);

            if (cloneArray)
            {
                this.array = (int[])array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(int[] array, int offset,
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
                        
            map.put("name", "IntIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
