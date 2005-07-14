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
package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class ByteIlaFromIntIla
{
    private ByteIlaFromIntIla() {}

    public static ByteIla create(IntIla intIla)
    {
    	Argument.assertNotNull(intIla, "intIla");
    	
		return new MyByteIla(intIla);
    }

    private static class MyByteIla extends AbstractByteIla
		implements ImmutableProxy
    {
		private IntIla intIla;

		MyByteIla(IntIla intIla)
		{
		    super(intIla.length() * 4);
		    
		    this.intIla = intIla;
		}

		protected void toArrayImpl(byte[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			IntIlaIterator iii = new IntIlaIterator(
				IntIlaSegment.create(intIla, start / 4,
				intIla.length() - start / 4));
			int col = (int)(start % 4);
			
			for (int totalElements=0 ; totalElements < length ; )
			{
				int elementsInRow = (int)Math.min(4 - col,
					length - totalElements);
				int value = iii.next();
				
				for (int c=col ; c < 4 && totalElements < length ; c++)
				{
					array[offset + totalElements++] =
						(byte)((value >>> (24 - (8 * c))) & 0xFF);
				}
				
				col = 0;
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ByteIlaFromIntIla");
			map.put("intIla", getImmutableInfo(intIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}