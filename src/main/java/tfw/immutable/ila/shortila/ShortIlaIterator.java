package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaIterator {
    private final ShortIla instance;
    private final long instanceLength;
    private long amountLeftToFetch;
    private final int bufferSize;
    private int amountToFetch;
    private short[] buffer;
    private int bufferIndex;
    private long fetchPosition;
    private long actualPosition;

    public static final int DEFAULT_BUFFER_SIZE = 10000;

    public ShortIlaIterator(ShortIla instance) {
        this(instance, DEFAULT_BUFFER_SIZE);
    }

    public ShortIlaIterator(ShortIla instance, int bufferSize) {
        Argument.assertNotNull(instance, "instance");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        this.instance = instance;
        this.instanceLength = instance.length();
        this.bufferSize = bufferSize;
        this.amountToFetch = bufferSize;
        this.buffer = new short[bufferSize];
        this.bufferIndex = bufferSize;
        this.actualPosition = 0;
        this.fetchPosition = 0;
        this.amountLeftToFetch = instanceLength - actualPosition;
    }

    public boolean hasNext() {
        return actualPosition < instanceLength;
    }

    /**
     * NOTE: returns GARBAGE if you fall off the end.
     * Either know the length of the ShortIla, or use hasNext()
     * properly.
     */
    public short next() throws DataInvalidException {
        // do we need to fetch into buffer?
        if (bufferIndex == bufferSize) {
            // how much do we fetch?
            if (amountLeftToFetch < bufferSize) {
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
     * Either know the length of the ShortIla, or use hasNext()
     * properly.
     */
    public void skip(long amount) throws DataInvalidException {
        long newBufferIndex = bufferIndex + amount;
        actualPosition += amount;

        // will we blow our cache?
        if (newBufferIndex > bufferSize) {
            amountLeftToFetch = instanceLength - actualPosition;
            fetchPosition = actualPosition;
            bufferIndex = bufferSize;
        } else {
            bufferIndex = (int) newBufferIndex;
        }
    }

    public long remaining() {
        return instanceLength - actualPosition;
    }
}
// AUTO GENERATED FROM TEMPLATE
