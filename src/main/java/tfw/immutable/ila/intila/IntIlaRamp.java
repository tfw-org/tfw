package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaRamp
{
    private IntIlaRamp()
    {
        // non-instantiable class
    }

    public static IntIla create(int startValue, int increment,
                                   long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyIntIla(startValue, increment, length);
    }

    private static class MyIntIla extends AbstractIntIla
        implements ImmutableProxy
    {
        private final int startValue;
        private final int increment;

        MyIntIla(int startValue, int increment, long length)
        {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(int[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            //int value = startValue;
            //for(long ii = 0; ii < start; ++ii)
            //{
            //    value += increment;
            //}
            
            // INCORRECT, BUT FAST
            int value = (int) (startValue + increment * start);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride, value += increment)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "IntIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Integer(startValue));
            map.put("increment", new Integer(increment));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
