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
package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class LongIlaFromByteIla
{
    private LongIlaFromByteIla() {}

    public static LongIla create(ByteIla byteIla)
    {
    	Argument.assertNotNull(byteIla, "byteIla");

		return new MyLongIla(byteIla);
    }

    private static class MyLongIla extends AbstractLongIla
		implements ImmutableProxy
    {
		private ByteIla byteIla;

		MyLongIla(ByteIla byteIla)
		{
		    super(byteIla.length() / 8);
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(long[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			ByteIlaIterator bii = new ByteIlaIterator(
				ByteIlaSegment.create(byteIla, 8 * start, 8 * length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+i] =
		    		(((long)bii.next() & 0xFF) << 56) |
					(((long)bii.next() & 0xFF) << 48) |
					(((long)bii.next() & 0xFF) << 40) |
					(((long)bii.next() & 0xFF) << 32) |
					(((long)bii.next() & 0xFF) << 24) |
					(((long)bii.next() & 0xFF) << 16) |
					(((long)bii.next() & 0xFF) <<  8) |
					(((long)bii.next() & 0xFF));
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LongIlaFromByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}
