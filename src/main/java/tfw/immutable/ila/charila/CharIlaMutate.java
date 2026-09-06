package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaMutate {
    private CharIlaMutate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long index, char value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new CharIlaImpl(ila, index, value);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final CharIla ila;
        private final long index;
        private final char value;

        private CharIlaImpl(CharIla ila, long index, char value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            IlaMutateUtil.get(
                    index,
                    start,
                    length,
                    offset,
                    (destinationOffset, sourceStart, amount) -> ila.get(array, destinationOffset, sourceStart, amount),
                    destinationOffset -> array[destinationOffset] = value);
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
