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
public final class FloatIlaConcatenate
{
    private FloatIlaConcatenate()
    {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        /*
          // this efficiency step would help out in a number
          // of situations, but could be confusing when the
          // immutable proxy getParameters() is called and you
          // don't see your concatenation!
        if(leftIla.length() == 0)
            return rightIla;
        if(rightIla.length() == 0)
            return leftIla;
        */
        return new MyFloatIla(leftIla, rightIla);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final FloatIla leftIla;
        private final FloatIla rightIla;
        private final long leftIlaLength;

        MyFloatIla(FloatIla leftIla, FloatIla rightIla)
        {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            if(start + length <= leftIlaLength)
            {
                leftIla.toArray(array, offset, stride, start, length);
            }
            else if(start >= leftIlaLength)
            {
                rightIla.toArray(array, offset, stride, start - leftIlaLength,
                                 length);
            }
            else
            {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, stride, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount * stride,
                                 stride, 0, length - leftAmount);
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaConcatenate");
            map.put("length", new Long(length()));
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
