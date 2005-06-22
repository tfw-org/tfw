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
package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class LongIlaFromDoubleIla
{
    private LongIlaFromDoubleIla() {}

    public static LongIla create(DoubleIla doubleIla)
    {
    	Argument.assertNotNull(doubleIla, "doubleIla");

		return new MyLongIla(doubleIla);
    }

    private static class MyLongIla extends AbstractLongIla
    {
		private DoubleIla doubleIla;

		MyLongIla(DoubleIla doubleIla)
		{
		    super(doubleIla.length());
		    
		    this.doubleIla = doubleIla;
		}

		protected void toArrayImpl(long[] array, int offset,
			long start, int length)
		{
			DoubleIlaIterator dii = new DoubleIlaIterator(
				DoubleIlaSegment.create(doubleIla, start, length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+i] = Double.doubleToRawLongBits(dii.next());
		    }
		}
    }
}