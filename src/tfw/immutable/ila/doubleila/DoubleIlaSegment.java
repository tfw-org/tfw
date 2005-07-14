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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class DoubleIlaSegment
{
    private DoubleIlaSegment() { }

    public static DoubleIla create(DoubleIla ila, long start)
    {
		return create(ila, start, ila.length() - start);
    }

    public static DoubleIla create(DoubleIla ila, long start, long length)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotLessThan(start, 0, "start");
    	Argument.assertNotLessThan(length, 0, "length");
    	Argument.assertNotGreaterThan((start + length), ila.length(),
    		"start + length", "ila.length()");

		return new MyDoubleIla(ila, start, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private DoubleIla ila;
		private long start;

		MyDoubleIla(DoubleIla ila, long start, long length)
		{
		    super(length);
		    
		    this.ila = ila;
		    this.start = start;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    ila.toArray(array, offset, this.start + start, length);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaSegment");
			map.put("ila", getImmutableInfo(ila));
			map.put("start", new Long(start));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}