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

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class DoubleIlaClip
{
	private DoubleIlaClip() {}
	
	public static DoubleIla create(DoubleIla doubleIla, double min, double max)
	{
		Argument.assertNotNull(doubleIla, "doubleIla");
		Argument.assertLessThan(min, max, "min", "max");
		
		return(new MyDoubleIla(doubleIla, min, max));
	}
	
	private static class MyDoubleIla extends AbstractDoubleIla
	{
		private final DoubleIla doubleIla;
		private final double min;
		private final double max;
		
		public MyDoubleIla(DoubleIla doubleIla, double min, double max)
		{
			super(doubleIla.length());
			
			this.doubleIla = doubleIla;
			this.min = min;
			this.max = max;
		}

		protected void toArrayImpl(double[] array, int offset, int stride,
			long start, int length) throws DataInvalidException
		{
			DoubleIlaIterator dii = new DoubleIlaIterator(
				DoubleIlaSegment.create(doubleIla, start, length));
			
			for (int i=0 ; i < length ; i++)
			{
				double d = dii.next();
				
				d = (d < min) ? min : d;
				d = (d > max) ? max : d;
				
				array[offset+(i * stride)] = d;
			}
		}
	}
}
