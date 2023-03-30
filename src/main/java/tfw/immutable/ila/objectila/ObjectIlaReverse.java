package tfw.immutable.ila.objectila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaReverse
{
    private ObjectIlaReverse()
    {
        // non-instantiable class
    }

    public static ObjectIla create(ObjectIla ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyObjectIla(ila);
    }

    private static class MyObjectIla extends AbstractObjectIla
        implements ImmutableProxy
    {
        private final ObjectIla ila;

        MyObjectIla(ObjectIla ila)
        {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(Object[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset + (length - 1) * stride,
                        -stride, length() - (start + length), length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ObjectIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
