package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaBound {
    private CharIlaBound() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, char minimum, char maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyCharIla(ila, minimum, maximum);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final char minimum;
        private final char maximum;

        MyCharIla(CharIla ila, char minimum, char maximum) {
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                char tmp = array[ii];
                if (tmp < minimum) {
                    array[ii] = minimum;
                } else if (tmp > maximum) {
                    array[ii] = maximum;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
