/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.immutable.ila.stringila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractStringIla
    extends AbstractIla
    implements StringIla
{
    protected abstract void toArrayImpl(String[] array, int offset,
                                        int stride, long start, int length)
        throws DataInvalidException;

    protected AbstractStringIla(long length)
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

    public final String[] toArray()
        throws DataInvalidException
    {
        if(length() > (long) Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException
                ("Ila too large for native array");

        return toArray((long) 0, (int) length());
    }

    public final String[] toArray(long start, int length)
        throws DataInvalidException
    {
        String[] result = new String[length];
        
        toArray(result, 0, start, length);
        
        return result;
    }

    public final void toArray(String[] array, int offset,
                              long start, int length)
        throws DataInvalidException
    {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(String[] array, int offset, int stride,
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
