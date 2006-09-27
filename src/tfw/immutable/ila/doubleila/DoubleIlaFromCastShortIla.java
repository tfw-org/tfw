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
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class DoubleIlaFromCastShortIla
{
    private DoubleIlaFromCastShortIla() {}

    public static DoubleIla create(ShortIla shortIla)
    {
    	Argument.assertNotNull(shortIla, "shortIla");

		return new MyDoubleIla(shortIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private ShortIla shortIla;

		MyDoubleIla(ShortIla shortIla)
		{
		    super(shortIla.length());
		    
		    this.shortIla = shortIla;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    ShortIlaIterator si = new ShortIlaIterator(
		    	ShortIlaSegment.create(shortIla, start, length));
		    
		    for (int i=0 ; si.hasNext() ; i++)
		    {
		    	array[offset+i] = (double)si.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaFromCastedShortIla");
			map.put("shortIla", getImmutableInfo(shortIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}