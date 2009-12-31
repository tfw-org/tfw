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
import tfw.immutable.ImmutableProxy;

public final class DoubleIlaRamp
{
    private DoubleIlaRamp() {}

    public static DoubleIla create(double startValue, double increment,
    	long length)
    {
    	Argument.assertNotLessThan(length, 0, "length");

		return new MyDoubleIla(startValue, increment, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    	implements ImmutableProxy
    {
		private final double startValue;
		private final double increment;

		MyDoubleIla(double startValue, double increment, long length)
		{
		    super(length);
		    
		    this.startValue = startValue;
		    this.increment = increment;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length)
		{
			double v = startValue + increment * start;
			
			for (int i=0 ; i < length ; i++,v+=increment)
			{
				array[offset+i] = v;
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "DoubleIlaRamp");
			map.put("startValue", new Double(startValue));
			map.put("increment", new Double(increment));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}