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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class DoubleIlaFromLongIla
{
    private DoubleIlaFromLongIla() {}

    public static DoubleIla create(LongIla longIla)
    {
    	Argument.assertNotNull(longIla, "longIla");

		return new MyDoubleIla(longIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
		implements ImmutableProxy
    {
		private LongIla longIla;

		MyDoubleIla(LongIla longIla)
		{
		    super(longIla.length());
		    
		    this.longIla = longIla;
		}

		protected void toArrayImpl(double[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			LongIlaIterator lii = new LongIlaIterator(
				LongIlaSegment.create(longIla, start, length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+(i * stride)] = Double.longBitsToDouble(lii.next());
		    }
		}
		
		public Map<String, Object> getParameters()
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("name", "DoubleIlaFromLongIla");
			map.put("longIla", getImmutableInfo(longIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}