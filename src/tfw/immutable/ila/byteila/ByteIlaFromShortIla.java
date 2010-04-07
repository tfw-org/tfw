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
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class ByteIlaFromShortIla
{
    private ByteIlaFromShortIla() {}

    public static ByteIla create(ShortIla shortIla)
    {
    	Argument.assertNotNull(shortIla, "shortIla");
    	
		return new MyByteIla(shortIla);
    }

    private static class MyByteIla extends AbstractByteIla
		implements ImmutableProxy
    {
		private ShortIla shortIla;

		MyByteIla(ShortIla shortIla)
		{
		    super(shortIla.length() * 2);
		    
		    this.shortIla = shortIla;
		}

		protected void toArrayImpl(byte[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			ShortIlaIterator sii = new ShortIlaIterator(
				ShortIlaSegment.create(shortIla, start / 2,
				shortIla.length() - start / 2));
			int col = (int)(start % 2);
			
			for (int totalElements=0 ; totalElements < length ; )
			{
				int value = sii.next();
				
				for (int c=col ; c < 2 && totalElements < length ; c++)
				{
					array[offset + (totalElements++ * stride)] =
						(byte)((value >>> (8 - (8 * c))) & 0xFF);
				}
				
				col = 0;
			}
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "ByteIlaFromIntIla");
			map.put("shortIla", getImmutableInfo(shortIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}