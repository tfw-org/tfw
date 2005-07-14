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

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.booleanilm.AbstractBooleanIlm;
import tfw.immutable.ilm.booleanilm.BooleanIlm;

public final class BooleanIlaFromBooleanIlm
{
    private BooleanIlaFromBooleanIlm() {}

    public static BooleanIla create(BooleanIlm ilm)
    {
    	Argument.assertNotNull(ilm, "ilm");

		return new MyBooleanIla(ilm);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
    	implements ImmutableProxy
    {
		private BooleanIlm ilm;

		MyBooleanIla(BooleanIlm ilm)
		{
		    super(ilm.width() * ilm.height());
		    
		    this.ilm = ilm;
		}

		protected void toArrayImpl(boolean[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			boolean[][] tempArray = new boolean[][] {array};
			long row = start / ilm.width();
			long col = start % ilm.width();
			
			for (int totalElements=0 ; totalElements < length ; )
			{
				int elementsInRow = (int)Math.min(ilm.width() - col,
					length - totalElements);
			
				ilm.toArray(tempArray, 0, offset + totalElements,
					row, col, elementsInRow, 1);
				
				col = 0;
				row++;
				totalElements += elementsInRow;
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "BooleanIlaFromBooleanIlm");
			map.put("ilm", AbstractBooleanIlm.getImmutableInfo(ilm));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}