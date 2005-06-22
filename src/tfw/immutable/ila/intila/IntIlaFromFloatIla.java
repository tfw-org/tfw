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
package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromFloatIla
{
    private IntIlaFromFloatIla() {}

    public static IntIla create(FloatIla floatIla)
    {
    	Argument.assertNotNull(floatIla, "floatIla");

		return new MyIntIla(floatIla);
    }

    private static class MyIntIla extends AbstractIntIla
    {
		private FloatIla floatIla;

		MyIntIla(FloatIla floatIla)
		{
		    super(floatIla.length());
		    
		    this.floatIla = floatIla;
		}

		protected void toArrayImpl(int[] array, int offset,
			long start, int length)
		{
			FloatIlaIterator fii = new FloatIlaIterator(
				FloatIlaSegment.create(floatIla, start, length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+i] = Float.floatToRawIntBits(fii.next());
		    }
		}
    }
}