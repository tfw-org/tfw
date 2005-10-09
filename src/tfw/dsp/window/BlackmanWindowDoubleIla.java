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
package tfw.dsp.window;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.AbstractDoubleIla;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class BlackmanWindowDoubleIla
{
	private static final double A0 = 0.42;
	private static final double A1 = 0.5;
	private static final double A2 = 0.08;
	
	private BlackmanWindowDoubleIla() {}
	
	public static DoubleIla create(DoubleIla doubleIla, int windowLength,
		long numberOfWindows)
	{
		return(new MyDoubleIla(doubleIla, windowLength));
	}
	
	private static class MyDoubleIla extends AbstractDoubleIla
	{
		private final DoubleIla doubleIla;
		private final double[] window;
		
		public MyDoubleIla(DoubleIla doubleIla, int windowLength)
		{
			super(doubleIla.length());
			
			this.doubleIla = doubleIla;
			this.window = new double[windowLength];
			
			double constant1 = 2.0 * Math.PI / (windowLength - 1);
			double constant2 = 2.0 * constant1;

			for (int i=0 ; i < windowLength ; i++)
			{
				window[i] = A0 - A1 * Math.cos(constant1 * i) +
					A2 * Math.cos(constant2 * i);
			}
		}
		
		public void toArrayImpl(double[] array, int offset, long start,
			int length) throws DataInvalidException
		{
			doubleIla.toArray(array, offset, start, length);
			
			for (int i=0,o=(int)(start%window.length) ; i < length ;
				i++,o%=window.length)
			{
				array[offset+i] *= window[o++];
			}
		}
	}
}