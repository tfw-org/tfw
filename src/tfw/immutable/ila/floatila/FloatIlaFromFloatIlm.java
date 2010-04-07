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
package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.floatilm.AbstractFloatIlm;
import tfw.immutable.ilm.floatilm.FloatIlm;

public final class FloatIlaFromFloatIlm
{
    private FloatIlaFromFloatIlm() {}

    public static FloatIla create(FloatIlm ilm)
    {
    	Argument.assertNotNull(ilm, "ilm");

		return new MyFloatIla(ilm);
    }

    private static class MyFloatIla extends AbstractFloatIla
    	implements ImmutableProxy
    {
		private FloatIlm ilm;

		MyFloatIla(FloatIlm ilm)
		{
		    super(ilm.width() * ilm.height());
		    
		    this.ilm = ilm;
		}

		protected void toArrayImpl(float[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			if (stride != 1) {
				throw new RuntimeException("stride != 1 not implemented");
			}
			float[][] tempArray = new float[][] {array};
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
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "FloatIlaFromFloatIlm");
			map.put("ilm", AbstractFloatIlm.getImmutableInfo(ilm));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}