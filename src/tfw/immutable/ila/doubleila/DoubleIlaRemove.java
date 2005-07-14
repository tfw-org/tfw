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

public final class DoubleIlaRemove
{
    private DoubleIlaRemove() {}

    public static DoubleIla create(DoubleIla ila, long index)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertLessThan(index, ila.length(),
    		"index", "ila.length()");

		return new MyDoubleIla(ila, index);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private DoubleIla ila;
		private long index;

		MyDoubleIla(DoubleIla ila, long index)
		{
		    super(ila.length() - 1);
		    
		    this.ila = ila;
		    this.index = index;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    if(index <= start)
		    {
				ila.toArray(array, offset, start + 1, length);
		    }
		    else if(index >= start + length)
		    {
				ila.toArray(array, offset, start, length);
		    }
		    else
		    {
				long indexMinusStart = index - start;
				ila.toArray(array, offset, start, (int) indexMinusStart);
				ila.toArray(array, (int) (offset + indexMinusStart),
					index + 1, (int) (length - indexMinusStart));
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaRemove");
			map.put("ila", getImmutableInfo(ila));
			map.put("index", new Long(index));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}