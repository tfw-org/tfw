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
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class DoubleIlaFromCastIntIla
{
    private DoubleIlaFromCastIntIla() {}

    public static DoubleIla create(IntIla intIla)
    {
    	Argument.assertNotNull(intIla, "intIla");

		return new MyDoubleIla(intIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private IntIla intIla;

		MyDoubleIla(IntIla intIla)
		{
		    super(intIla.length());
		    
		    this.intIla = intIla;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    IntIlaIterator ii = new IntIlaIterator(
		    	IntIlaSegment.create(intIla, start, length));
		    
		    for (int i=0 ; ii.hasNext() ; i++)
		    {
		    	array[offset+i] = (double)ii.next();
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaFromCastedIntIla");
			map.put("intIla", getImmutableInfo(intIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}