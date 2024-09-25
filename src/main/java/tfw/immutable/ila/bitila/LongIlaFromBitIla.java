package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.longila.AbstractLongIla;
import tfw.immutable.ila.longila.LongIla;

public final class LongIlaFromBitIla {
    private LongIlaFromBitIla() {}

    public static LongIla create(final BitIla bitIla) {
        return new LongIlaImpl(bitIla);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final BitIla bitIla;

        public LongIlaImpl(final BitIla bitIla) {
            Argument.assertNotNull(bitIla, "bitIla");

            this.bitIla = bitIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return bitIla.lengthInBits() / Long.SIZE + bitIla.lengthInBits() % Long.SIZE == 0 ? 0 : 1;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            final long startInBits = start * Long.SIZE;
            final long lengthInBits = Math.min(length * (long) Long.SIZE, startInBits + bitIla.lengthInBits());

            bitIla.get(array, offset, startInBits, lengthInBits);
        }
    }
}
