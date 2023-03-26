package tfw.immutable.ila;

import tfw.check.Argument;

public abstract class AbstractIla implements ImmutableLongArray
{
    protected final long length;
    
    protected AbstractIla(long length)
    {
        Argument.assertNotLessThan(length, 0, "length");
        
        this.length = length;
    }

    public final long length()
    {
        return length;
    }

    protected final void boundsCheck(int arrayLength, int offset,
                                     int stride, long start, int length)
    {
    	AbstractIlaCheck.boundsCheck(this.length, arrayLength, offset, stride, start, length);
    }
}
