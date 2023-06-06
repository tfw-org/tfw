package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaConcatenate {
    private StringIlaConcatenate() {
        // non-instantiable class
    }

    public static StringIla create(StringIla leftIla, StringIla rightIla) {
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
        return new MyStringIla(leftIla, rightIla);
    }

    private static class MyStringIla extends AbstractStringIla implements ImmutableProxy {
        private final StringIla leftIla;
        private final StringIla rightIla;
        private final long leftIlaLength;

        MyStringIla(StringIla leftIla, StringIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(String[] array, int offset, int stride, long start, int length)
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

            map.put("name", "StringIlaConcatenate");
            map.put("length", new Long(length()));
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
