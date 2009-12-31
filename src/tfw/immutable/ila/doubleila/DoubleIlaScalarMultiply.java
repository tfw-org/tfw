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
import tfw.immutable.ila.doubleila.AbstractDoubleIla;
import tfw.immutable.ila.doubleila.DoubleIla;

public class DoubleIlaScalarMultiply
{
	private DoubleIlaScalarMultiply() {}
	
	public static DoubleIla create(DoubleIla doubleIla, double value)
	{
		Argument.assertNotNull(doubleIla, "doubleIla");
		
		return(new MyDoubleIla(doubleIla, value));
	}
	
	private static class MyDoubleIla extends AbstractDoubleIla
	{
		private final DoubleIla doubleIla;
		private final double value;
		
		public MyDoubleIla(DoubleIla doubleIla, double value)
		{
			super(doubleIla.length());
			
			this.doubleIla = doubleIla;
			this.value = value;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			doubleIla.toArray(array, offset, start, length);
			
			for (int i=0 ; i < length ; i++)
			{
				array[offset + i] *= value;
			}
		}
	}
}
