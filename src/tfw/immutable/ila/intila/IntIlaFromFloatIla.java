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
package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromFloatIla
{
    private IntIlaFromFloatIla() {}

    public static IntIla create(FloatIla floatIla)
    {
    	Argument.assertNotNull(floatIla, "floatIla");

		return new MyIntIla(floatIla);
    }

    private static class MyIntIla extends AbstractIntIla
		implements ImmutableProxy
    {
		private FloatIla floatIla;

		MyIntIla(FloatIla floatIla)
		{
		    super(floatIla.length());
		    
		    this.floatIla = floatIla;
		}

		protected void toArrayImpl(int[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			FloatIlaIterator fii = new FloatIlaIterator(
				FloatIlaSegment.create(floatIla, start, length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+(i * stride)] = Float.floatToRawIntBits(fii.next());
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "IntIlaFromFloatIla");
			map.put("floatIla", getImmutableInfo(floatIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}