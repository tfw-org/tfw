package tfw.immutable.stream;

import java.io.IOException;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleStreamFromDoubleIla {
    private DoubleStreamFromDoubleIla() {}

    public static DoubleStream create(final DoubleIla ila) throws IOException {
        return StreamSupport.doubleStream(new DoubleIlaSpliterator(ila), false);
    }

    private static class DoubleIlaSpliterator extends AbstractStreamFromIla<Double> implements Spliterator.OfDouble {
        private final DoubleIla ila;
        private final double[] array = new double[1];

        private int position = 0;

        public DoubleIlaSpliterator(final DoubleIla ila) throws IOException {
            super(ila.length());

            this.ila = ila;
        }

        @Override
        public OfDouble trySplit() {
            return null;
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
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
