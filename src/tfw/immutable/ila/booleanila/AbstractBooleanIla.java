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
package tfw.immutable.ila.booleanila;

import tfw.immutable.ila.AbstractIla;

public abstract class AbstractBooleanIla extends AbstractIla implements BooleanIla
{
    protected abstract void toArrayImpl(boolean[] array, int offset,
			long start, int length);

    protected AbstractBooleanIla(long length)
    {
    	super(length);
    }

    public final boolean[] toArray()
    {
    	if(length() > (long) Integer.MAX_VALUE)
    		throw new ArrayIndexOutOfBoundsException
				("Ila too large for native array");
    	
    	return toArray((long) 0, (int) length());
    }

    public final boolean[] toArray(long start, int length)
    {
    	boolean[] result = new boolean[length];
    	
    	toArray(result, 0, start, length);
    	
    	return result;
    }

    public final void toArray(boolean[] array, int offset, long start, int length)
    {
    	if (length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array.length, offset, start, length);
    	toArrayImpl(array, offset, start, length);
    }
}