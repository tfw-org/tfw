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
package tfw.immutable.ilm.booleanilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.booleanila.AbstractBooleanIla;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlmFromBooleanIla
{
    private BooleanIlmFromBooleanIla() {}

    public static BooleanIlm create(BooleanIla ila, long width)
    {
    	Argument.assertNotNull(ila, "ila");
		if (ila.length() % width != 0)
			throw new IllegalArgumentException(
				"length not divisible by width!");
				
		return new MyBooleanIlm(ila, width);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm
    	implements ImmutableProxy
    {
		private BooleanIla ila;

		MyBooleanIlm(BooleanIla ila, long width)
		{
		    super(width, ila.length() / width);
		    
		    this.ila = ila;
		}
		
		protected void toArrayImpl(boolean[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
			for (int r=0 ; r < height ; r++)
			{
				ila.toArray(array[rowOffset+r], columnOffset,
					(rowStart + r) * this.width + columnStart, width);
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "BooleanIlmFromBooleanIla");
			map.put("ila", AbstractBooleanIla.getImmutableInfo(ila));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}