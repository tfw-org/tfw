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
public final class ShortIlaSegment
{
    private ShortIlaSegment()
    {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long start)
    {
        return create(ila, start, ila.length() - start);
    }

    public static ShortIla create(ShortIla ila, long start, long length)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan((start + length), ila.length(),
                                      "start + length", "ila.length()");

        return new MyShortIla(ila, start, length);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final ShortIla ila;
        private final long start;

        MyShortIla(ShortIla ila, long start, long length)
        {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(short[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ShortIlaSegment");
            map.put("length", new Long(length()));
            map.put("start", new Long(start));
            map.put("ila", getImmutableInfo(ila));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
