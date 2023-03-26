package tfw.immutable.ila.objectila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFill
{
    private ObjectIlaFill()
    {
        // non-instantiable class
    }

    public static ObjectIla create(Object value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyObjectIla(value, length);
    }

    private static class MyObjectIla extends AbstractObjectIla
        implements ImmutableProxy
    {
        private final Object value;

        MyObjectIla(Object value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(Object[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ObjectIlaFill");
            map.put("length", new Long(length()));
            map.put("value", value);

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
