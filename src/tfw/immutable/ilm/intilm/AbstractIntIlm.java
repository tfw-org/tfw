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
package tfw.immutable.ilm.intilm;

import tfw.check.Argument;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractIntIlm extends AbstractIlm implements IntIlm
{
    protected abstract void toArrayImpl(int[][] array, int xOffset, int yOffset,
			long xStart, long yStart, int xLength, int yLength);

    protected AbstractIntIlm(long width, long height)
    {
    	super(width, height);
    }

    public final int[][] toArray()
    {
    	Argument.assertNotGreaterThan(width(), Integer.MAX_VALUE,
    		"width()", "native array size");
    	Argument.assertNotGreaterThan(height(), Integer.MAX_VALUE,
    		"height()", "native array size");
    	
    	return toArray(0, 0, (int)width(), (int)height());
    }

    public final int[][] toArray(long rowStart, long columnStart,
    	int width, int height)
    {
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	
    	int[][] result = new int[height][width];
    	
    	toArray(result, 0, 0, rowStart, columnStart, width, height);
    	
    	return result;
    }

    public final void toArray(int[][] array, int rowOffset, int columnOffset,
    	long rowStart, long columnStart, int width, int height)
    {
    	Argument.assertNotNull(array, "array");
    	
    	if (width == 0 || height == 0 || array.length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array[0].length, array.length, rowOffset, columnOffset,
    		rowStart, columnStart, width, height);
    	toArrayImpl(array, rowOffset, columnOffset, rowStart, columnStart,
    		width, height);
    }
}