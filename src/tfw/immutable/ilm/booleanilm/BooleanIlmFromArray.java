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
package tfw.immutable.ilm.booleanilm;

import tfw.check.Argument;

public final class BooleanIlmFromArray
{
    private BooleanIlmFromArray() {}

    public static BooleanIlm create(boolean[][] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyBooleanIlm(array);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    {
		private final boolean[][] array;

		MyBooleanIlm(boolean[][] array)
		{
		    super(array.length > 0 ? array[0].length : 0, array.length);
		    
		    this.array = (boolean[][])array.clone();
		    
		    for (int i=0 ; i < array.length ; i++)
		    {
		    	Argument.assertNotNull(array[i], "array["+i+"]");
		    	Argument.assertEquals(array[i].length, width,
		    		"array["+i+"].length", "width");
		    	
		    	this.array[i] = (boolean[])array[i].clone();
		    }
		}

		protected void toArrayImpl(boolean[][] array, int rowOffset, int columnOffset,
			long rowStart, long columnStart, int width, int height)
		{
			for (int i=0 ; i < height ; i++)
			{
				System.arraycopy(this.array[(int)(rowStart + i)], (int)columnStart,
					array[rowOffset+i], columnOffset, width);
			} 
		}
    }
}
