package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaIterator {
    private final BooleanIla instance;
    private final long instanceLength;
    private long amountLeftToFetch;
    private int amountToFetch;
    private boolean[] buffer;
    private int bufferIndex;
    private long fetchPosition;
    private long actualPosition;

    public static final int DEFAULT_BUFFER_SIZE = 10000;

    public BooleanIlaIterator(BooleanIla instance, boolean[] buffer) {
        Argument.assertNotNull(instance, "instance");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        this.instance = instance;
        this.instanceLength = instance.length();
        this.amountToFetch = buffer.length;
        this.buffer = buffer;
        this.bufferIndex = buffer.length;
        this.actualPosition = 0;
        this.fetchPosition = 0;
        this.amountLeftToFetch = instanceLength - actualPosition;
    }

    public boolean hasNext() {
        return actualPosition < instanceLength;
    }

    /**
     * NOTE: returns GARBAGE if you fall off the end.
     * Either know the length of the BooleanIla, or use hasNext()
     * properly.
     */
    public boolean next() throws DataInvalidException {
        // do we need to fetch into buffer?
        if (bufferIndex == buffer.length) {
            // how much do we fetch?
            if (amountLeftToFetch < buffer.length) {
                amountToFetch = (int) amountLeftToFetch;
            }

            amountLeftToFetch -= amountToFetch;
            instance.toArray(buffer, 0, fetchPosition, amountToFetch);
            fetchPosition += amountToFetch;
            bufferIndex = 0;
        }
        actualPosition++;
        return buffer[bufferIndex++];
    }

    /**
     * Can dork you if you skip enough to fall off end.
     * Either know the length of the BooleanIla, or use hasNext()
     * properly.
     */
    public void skip(long amount) throws DataInvalidException {
        long newBufferIndex = bufferIndex + amount;
        actualPosition += amount;

        // will we blow our cache?
        if (newBufferIndex > buffer.length) {
            amountLeftToFetch = instanceLength - actualPosition;
            fetchPosition = actualPosition;
            bufferIndex = buffer.length;
        } else {
            bufferIndex = (int) newBufferIndex;
        }
    }

    public long remaining() {
        return instanceLength - actualPosition;
    }
}
// AUTO GENERATED FROM TEMPLATE
