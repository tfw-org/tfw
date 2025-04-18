package tfw.immutable.stream;

import java.io.IOException;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import tfw.immutable.ila.longila.LongIla;

public final class LongStreamFromLongIla {
    private LongStreamFromLongIla() {}

    public static LongStream create(final LongIla ila) {
        return StreamSupport.longStream(new LongIlaSpliterator(ila), false);
    }

    private static class LongIlaSpliterator implements Spliterator.OfLong {
        private final LongIla ila;
        private final long[] array = new long[1];

        private int position = 0;

        public LongIlaSpliterator(final LongIla ila) {
            this.ila = ila;
        }

        @Override
        public long estimateSize() {
            try {
                return ila.length();
            } catch (IOException e) {
                return 0;
            }
        }

        @Override
        public int characteristics() {
            return IMMUTABLE | ORDERED | SIZED;
        }

        @Override
        public OfLong trySplit() {
            return null;
        }

        @Override
        public boolean tryAdvance(LongConsumer action) {
            try {
                if (position < ila.length()) {
                    ila.get(array, 0, position++, 1);
                    action.accept(array[0]);

                    return true;
                }
            } catch (IOException e) {
                // do nothing and return false.
            }

            return false;
        }
    }
}
