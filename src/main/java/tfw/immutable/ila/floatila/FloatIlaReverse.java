package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaReverse
{
    private FloatIlaReverse()
    {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyFloatIla(ila);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final FloatIla ila;

        MyFloatIla(FloatIla ila)
        {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset + (length - 1) * stride,
                        -stride, length() - (start + length), length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
