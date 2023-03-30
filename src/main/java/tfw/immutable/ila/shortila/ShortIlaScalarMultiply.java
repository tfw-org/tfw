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
public final class ShortIlaScalarMultiply
{
    private ShortIlaScalarMultiply()
    {
    	// non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short scalar)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyShortIla(ila, scalar);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final ShortIla ila;
        private final short scalar;

        MyShortIla(ShortIla ila, short scalar)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] *= scalar;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaScalarMultiply");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Short(scalar));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
