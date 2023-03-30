package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaReverse
{
    private CharIlaReverse()
    {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyCharIla(ila);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final CharIla ila;

        MyCharIla(CharIla ila)
        {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset + (length - 1) * stride,
                        -stride, length() - (start + length), length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "CharIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
