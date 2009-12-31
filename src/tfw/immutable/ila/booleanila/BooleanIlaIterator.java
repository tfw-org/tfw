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
package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class BooleanIlaIterator
{
    private BooleanIla instance;
    private long amountLeftToFetch;
    private int bufferSize;
    private int amountToFetch;
    private boolean[] buffer;
    private int bufferIndex;
    private long position = 0;
    public static final int DEFAULT_BUFFER_SIZE = 10000;

    public BooleanIlaIterator(BooleanIla instance)
    {
		this(instance, DEFAULT_BUFFER_SIZE);
    }

    public BooleanIlaIterator(BooleanIla instance, int bufferSize)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

		this.instance = instance;
		this.amountLeftToFetch = instance.length();
		this.bufferSize = bufferSize;
		this.amountToFetch = bufferSize;
		this.buffer = new boolean[bufferSize];
		this.bufferIndex = bufferSize;
    }

    public boolean hasNext()
    {
		return (amountLeftToFetch == 0 ? bufferIndex != amountToFetch : true);
    }

    /**
       NOTE: returns GARBAGE if you fall off the end.
       Either know the length of the DoubleIla, or use hasNext()
       properly.
     */
    public boolean next() throws DataInvalidException
    {
		// do we need to fetch into buffer?
		if (bufferIndex == bufferSize)
		{
		    // how much do we fetch?
		    if (amountLeftToFetch < bufferSize)
		    {
				amountToFetch = (int) amountLeftToFetch;
		    }

		    amountLeftToFetch -= amountToFetch;
		    instance.toArray(buffer, 0, position, amountToFetch);
		    position += amountToFetch;
		    bufferIndex = 0;
		}
		return buffer[bufferIndex++];
    }
}