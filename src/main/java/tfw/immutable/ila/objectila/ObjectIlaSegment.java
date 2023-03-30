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
public final class ObjectIlaSegment
{
    private ObjectIlaSegment()
    {
        // non-instantiable class
    }

    public static ObjectIla create(ObjectIla ila, long start)
    {
        return create(ila, start, ila.length() - start);
    }

    public static ObjectIla create(ObjectIla ila, long start, long length)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan((start + length), ila.length(),
                                      "start + length", "ila.length()");

        return new MyObjectIla(ila, start, length);
    }

    private static class MyObjectIla extends AbstractObjectIla
        implements ImmutableProxy
    {
        private final ObjectIla ila;
        private final long start;

        MyObjectIla(ObjectIla ila, long start, long length)
        {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(Object[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ObjectIlaSegment");
            map.put("length", new Long(length()));
            map.put("start", new Long(start));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
