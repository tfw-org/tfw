package tfw.immutable.stream;

import java.io.IOException;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import tfw.immutable.ila.intila.IntIla;

public final class IntStreamFromIntIla {
    private IntStreamFromIntIla() {}

    public static IntStream create(final IntIla ila) throws IOException {
        return StreamSupport.intStream(new IntIlaSpliterator(ila), false);
    }

    private static class IntIlaSpliterator extends AbstractStreamFromIla<Integer> implements Spliterator.OfInt {
        private final IntIla ila;
        private final int[] array = new int[1];

        private int position = 0;

        public IntIlaSpliterator(final IntIla ila) throws IOException {
            super(ila.length());

            this.ila = ila;
        }

        @Override
        public OfInt trySplit() {
            return null;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
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
