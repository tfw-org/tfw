package tfw.immutable.ila.shortila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractShortIla
    extends AbstractIla
    implements ShortIla
{
    protected abstract void toArrayImpl(short[] array, int offset,
                                        int stride, long start, int length)
        throws DataInvalidException;

    protected AbstractShortIla(long length)
    {
        super(length);
    }
    
    public static Object getImmutableInfo(ImmutableLongArray ila)
    {
        if(ila instanceof ImmutableProxy)
        {
            return(((ImmutableProxy) ila).getParameters());
        }
        else
        {
            return(ila.toString());
        }
    }

    public final short[] toArray()
        throws DataInvalidException
    {
        if(length() > (long) Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException
                ("Ila too large for native array");

        return toArray((long) 0, (int) length());
    }

    public final short[] toArray(long start, int length)
        throws DataInvalidException
    {
        short[] result = new short[length];
        
        toArray(result, 0, start, length);
        
        return result;
    }

    public final void toArray(short[] array, int offset,
                              long start, int length)
        throws DataInvalidException
    {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(short[] array, int offset, int stride,
                              long start, int length)
        throws DataInvalidException
    {
        if(length == 0)
        {
            return;
        }
        
        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
