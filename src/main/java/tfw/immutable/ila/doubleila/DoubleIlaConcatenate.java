package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaConcatenate {
    private DoubleIlaConcatenate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla) {
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
        return new MyDoubleIla(leftIla, rightIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final DoubleIla leftIla;
        private final DoubleIla rightIla;
        private final long leftIlaLength;

        MyDoubleIla(DoubleIla leftIla, DoubleIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            if (start + length <= leftIlaLength) {
                leftIla.toArray(array, offset, stride, start, length);
            } else if (start >= leftIlaLength) {
                rightIla.toArray(array, offset, stride, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, stride, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount * stride, stride, 0, length - leftAmount);
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaConcatenate");
            map.put("length", new Long(length()));
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
