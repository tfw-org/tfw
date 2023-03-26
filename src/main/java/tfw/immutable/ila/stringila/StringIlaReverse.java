package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaReverse
{
    private StringIlaReverse()
    {
        // non-instantiable class
    }

    public static StringIla create(StringIla ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyStringIla(ila);
    }

    private static class MyStringIla extends AbstractStringIla
        implements ImmutableProxy
    {
        private final StringIla ila;

        MyStringIla(StringIla ila)
        {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(String[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset + (length - 1) * stride,
                        -stride, length() - (start + length), length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "StringIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
