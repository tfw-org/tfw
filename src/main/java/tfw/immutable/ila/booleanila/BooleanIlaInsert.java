package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaInsert
{
    private BooleanIlaInsert()
    {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long index, boolean value)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index",
                                      "ila.length()");

        return new MyBooleanIla(ila, index, value);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
        implements ImmutableProxy
    {
        private final BooleanIla ila;
        private final long index;
        private final boolean value;

        MyBooleanIla(BooleanIla ila, long index, boolean value)
        {
            super(ila.length() + 1);
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(boolean[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long startPlusLength = start + length;

            if(index < start)
            {
                ila.toArray(array, offset, stride, start - 1, length);
            }
            else if(index >= startPlusLength)
            {
                ila.toArray(array, offset, stride, start, length);
            }
            else
            {
                final int indexMinusStart = (int) (index - start);
                if(index > start)
                {
                    ila.toArray(array, offset, stride, start,
                                indexMinusStart);
                }
                array[offset + indexMinusStart * stride] = value;
                if(index < startPlusLength - 1)
                {
                    ila.toArray(array, offset + (indexMinusStart + 1) * stride,
                                stride, index, length - indexMinusStart - 1);
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "BooleanIlaInsert");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
            map.put("index", new Long(index));
            map.put("value", (value? Boolean.TRUE : Boolean.FALSE));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
