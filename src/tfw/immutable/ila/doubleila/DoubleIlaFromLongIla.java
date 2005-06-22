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

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class DoubleIlaFromLongIla
{
    private DoubleIlaFromLongIla() {}

    public static DoubleIla create(LongIla longIla)
    {
    	Argument.assertNotNull(longIla, "longIla");

		return new MyDoubleIla(longIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
    {
		private LongIla longIla;

		MyDoubleIla(LongIla longIla)
		{
		    super(longIla.length());
		    
		    this.longIla = longIla;
		}

		protected void toArrayImpl(double[] array, int offset,
			long start, int length)
		{
			LongIlaIterator lii = new LongIlaIterator(
				LongIlaSegment.create(longIla, start, length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+i] = Double.longBitsToDouble(lii.next());
		    }
		}
    }
}