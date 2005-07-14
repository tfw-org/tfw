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
 * witout even the implied warranty of
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
package tfw.immutable.ila.intila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.ImmutableLongArray;

public abstract class AbstractIntIla extends AbstractIla
	implements IntIla
{
    protected abstract void toArrayImpl(int[] array, int offset,
			long start, int length) throws DataInvalidException;

    protected AbstractIntIla(long length)
    {
    	super(length);
    }
    
    public static Object getImmutableInfo(ImmutableLongArray ila)
    {
    	if (ila instanceof ImmutableProxy)
    	{
    		return(((ImmutableProxy)ila).getParameters());
    	}
    	else
    	{
    		return(ila.toString());
    	}
    }

    public final int[] toArray()
    	throws DataInvalidException
    {
    	if(length() > (long) Integer.MAX_VALUE)
    		throw new ArrayIndexOutOfBoundsException
				("Ila too large for native array");
    	
    	return toArray((long) 0, (int) length());
    }

    public final int[] toArray(long start, int length)
    	throws DataInvalidException
    {
    	int[] result = new int[length];
    	
    	toArray(result, 0, start, length);
    	
    	return result;
    }

    public final void toArray(int[] array, int offset,
    	long start, int length) throws DataInvalidException
    {
    	if (length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array.length, offset, start, length);
    	toArrayImpl(array, offset, start, length);
    }
}