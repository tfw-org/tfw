package tfw.immutable.stream;

import java.util.Spliterator;

abstract class AbstractStreamFromIla<T> implements Spliterator<T> {
    private final long ilaLength;

    AbstractStreamFromIla(final long ilaLength) {
        this.ilaLength = ilaLength;
    }

    @Override
    public final long estimateSize() {
        return ilaLength;
    }

    @Override
    public final int characteristics() {
        return IMMUTABLE | ORDERED | SIZED;
    }
}
