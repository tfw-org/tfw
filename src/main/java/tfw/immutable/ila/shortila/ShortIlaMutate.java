package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaMutate
{
    private ShortIlaMutate()
    {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long index, short value)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyShortIla(ila, index, value);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final ShortIla ila;
        private final long index;
        private final short value;

        MyShortIla(ShortIla ila, long index, short value)
        {
            super(ila.length());
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            final long startPlusLength = start + length;

            if(index < start || index >= startPlusLength)
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
                if(index <= startPlusLength)
                {
                    ila.toArray(array, offset + (indexMinusStart + 1) * stride,
                                stride, index + 1, length - 
                                indexMinusStart - 1);
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaMutate");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
            map.put("index", new Long(index));
            map.put("value", new Short(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
