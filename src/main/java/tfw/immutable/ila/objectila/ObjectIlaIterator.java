package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaIterator<T>
{
    private final ObjectIla<T> instance;
    private final long instanceLength;
    private final T[] buffer;
    
    private long amountLeftToFetch;
    private int amountToFetch;
    private int bufferIndex;
    private long fetchPosition;
    private long actualPosition;

    public ObjectIlaIterator(final ObjectIla<T> instance, final T[] buffer)
    {
        Argument.assertNotNull(instance, "instance");
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

    public boolean hasNext()
    {
        return actualPosition < instanceLength;
    }

    /**
       NOTE: returns GARBAGE if you fall off the end.
       Either know the length of the ObjectIla, or use hasNext()
       properly.
    */
    public T next() throws DataInvalidException
    {
        // do we need to fetch into buffer?
        if (bufferIndex == buffer.length)
        {
            // how much do we fetch?
            if (amountLeftToFetch < buffer.length)
            {
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
       Can dork you if you skip enough to fall off end.
       Either know the length of the ObjectIla, or use hasNext()
       properly.
    */
    public void skip(final long amount)
    {
        final long newBufferIndex = bufferIndex + amount;
        
        actualPosition += amount;

        // will we blow our cache?
        if (newBufferIndex > buffer.length)
        {
            amountLeftToFetch = instanceLength - actualPosition;
            fetchPosition = actualPosition;
            bufferIndex = buffer.length;
        }
        else
        {
            bufferIndex = (int) newBufferIndex;
        }
    }

    public long remaining()
    {
        return instanceLength - actualPosition;
    }
}
// AUTO GENERATED FROM TEMPLATE
