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
public final class CharIlaBound
{
    private CharIlaBound()
    {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, char minimum,
        char maximum)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyCharIla(ila, minimum, maximum);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final CharIla ila;
        private final char minimum;
        private final char maximum;

        MyCharIla(CharIla ila, char minimum, char maximum)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                char tmp = array[ii];
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
                        
            map.put("name", "CharIlaBound");
            map.put("ila", getImmutableInfo(ila));
            map.put("minimum", new Character(minimum));
            map.put("maximum", new Character(maximum));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
