/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class DoubleIlaFromCastFloatIla
{
    private DoubleIlaFromCastFloatIla() {}

    public static DoubleIla create(FloatIla floatIla)
    {
    	Argument.assertNotNull(floatIla, "floatIla");

		return new MyDoubleIla(floatIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private FloatIla floatIla;

		MyDoubleIla(FloatIla floatIla)
		{
		    super(floatIla.length());
		    
		    this.floatIla = floatIla;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    FloatIlaIterator fi = new FloatIlaIterator(
		    	FloatIlaSegment.create(floatIla, start, length));
		    
		    for (int i=0 ; fi.hasNext() ; i++)
		    {
		    	array[offset+i] = (double)fi.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaFromCastedFloatIla");
			map.put("floatIla", getImmutableInfo(floatIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}