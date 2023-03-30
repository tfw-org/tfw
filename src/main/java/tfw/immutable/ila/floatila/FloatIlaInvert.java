package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=floating
 */
public final class FloatIlaInvert
{
    private FloatIlaInvert()
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
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (float) 1 / array[ii];
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaInvert");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
