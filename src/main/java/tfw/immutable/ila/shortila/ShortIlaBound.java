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
public final class ShortIlaBound
{
    private ShortIlaBound()
    {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short minimum,
        short maximum)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyShortIla(ila, minimum, maximum);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final ShortIla ila;
        private final short minimum;
        private final short maximum;

        MyShortIla(ShortIla ila, short minimum, short maximum)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                short tmp = array[ii];
                if (tmp < minimum)
                {
                    array[ii] = minimum;
                }
                else if (tmp > maximum)
                {
                    array[ii] = maximum;
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaBound");
            map.put("ila", getImmutableInfo(ila));
            map.put("minimum", new Short(minimum));
            map.put("maximum", new Short(maximum));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
