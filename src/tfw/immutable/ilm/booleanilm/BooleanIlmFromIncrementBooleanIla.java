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

import java.math.BigDecimal;
import java.util.Arrays;
import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlmFromIncrementBooleanIla
{
    private BooleanIlmFromIncrementBooleanIla() {}

    public static BooleanIlm create(BooleanIla ila, BigDecimal rowIncrement,
    	boolean noDataValue)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotNull(rowIncrement, "rowIncrement");
				
		return new MyBooleanIlm(ila, rowIncrement, noDataValue);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    {
		private final BooleanIla ila;
		private final BigDecimal rowIncrement;
		private final boolean noDataValue;

		MyBooleanIlm(BooleanIla ila, BigDecimal rowIncrement, boolean noDataValue)
		{
		    super(ila.length(), new BigDecimal(ila.length()).divide(
		    	rowIncrement, BigDecimal.ROUND_UP).longValue());
		    
		    this.ila = ila;
		    this.rowIncrement = rowIncrement;
		    this.noDataValue = noDataValue;
		}
		
		protected void toArrayImpl(boolean[][] array, int rowOffset, int columnOffset,
			long rowStart, long columnStart, int width, int height)
		{
			for (int r=0 ; r < height ; r++)
			{
				long startIlaFromRowIndex = new BigDecimal(r+rowStart).multiply(rowIncrement).longValue();
				long ilaEndIndex = ila.length() - startIlaFromRowIndex - 1;
				long ilaStart = startIlaFromRowIndex + columnStart;

			    if (columnStart + width - 1 <= ilaEndIndex)
			    {
					ila.toArray(array[r+rowOffset], columnOffset, ilaStart, width);
			    }
			    else if (columnStart > ilaEndIndex)
				{
					Arrays.fill(array[r+rowOffset], columnOffset, columnOffset + width, noDataValue);
			    }
			    else
				{
					int ilaSubsetLength = (int)(ilaEndIndex - columnStart + 1);

					ila.toArray(array[r+rowOffset], columnOffset, ilaStart, ilaSubsetLength);
					Arrays.fill(array[r+rowOffset], columnOffset + ilaSubsetLength,
						columnOffset + width, noDataValue);
				}
	    	}
		}
    }
}