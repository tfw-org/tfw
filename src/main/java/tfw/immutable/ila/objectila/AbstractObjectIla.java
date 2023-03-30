package tfw.immutable.ila.objectila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractObjectIla
    extends AbstractIla
    implements ObjectIla
{
    protected abstract void toArrayImpl(Object[] array, int offset,
                                        int stride, long start, int length)
        throws DataInvalidException;

    protected AbstractObjectIla(long length)
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

    public final Object[] toArray()
        throws DataInvalidException
    {
        if(length() > (long) Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException
                ("Ila too large for native array");

        return toArray((long) 0, (int) length());
    }

    public final Object[] toArray(long start, int length)
        throws DataInvalidException
    {
        Object[] result = new Object[length];
        
        toArray(result, 0, start, length);
        
        return result;
    }

    public final void toArray(Object[] array, int offset,
                              long start, int length)
        throws DataInvalidException
    {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(Object[] array, int offset, int stride,
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
