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
package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class ByteIlaFromLongIla
{
    private ByteIlaFromLongIla() {}

    public static ByteIla create(LongIla longIla)
    {
    	Argument.assertNotNull(longIla, "longIla");
    	
		return new MyByteIla(longIla);
    }

    private static class MyByteIla extends AbstractByteIla
		implements ImmutableProxy
    {
		private LongIla longIla;

		MyByteIla(LongIla longIla)
		{
		    super(longIla.length() * 8);
		    
		    this.longIla = longIla;
		}

		protected void toArrayImpl(byte[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			LongIlaIterator lii = new LongIlaIterator(
					LongIlaSegment.create(longIla, start / 8,
					longIla.length() - start / 8));
			int col = (int)(start % 8);
				
			for (int totalElements=0 ; totalElements < length ; )
			{
				long value = lii.next();
					
				for (int c=col ; c < 8 && totalElements < length ; c++)
				{
					array[offset + (totalElements++ * stride)] =
						(byte)((value >>> (56 - (8 * c))) & 0xFF);
				}
					
				col = 0;
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ByteIlaFromLongIla");
			map.put("longIla", getImmutableInfo(longIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}