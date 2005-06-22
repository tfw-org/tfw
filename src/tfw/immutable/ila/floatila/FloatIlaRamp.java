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
package tfw.immutable.ila.floatila;

import tfw.check.Argument;

public final class FloatIlaRamp
{
    private FloatIlaRamp() {}

    public static FloatIla create(float startValue, float increment,
    	long length)
    {
    	Argument.assertNotLessThan(length, 0, "length");

		return new MyFloatIla(startValue, increment, length);
    }

    private static class MyFloatIla extends AbstractFloatIla
    {
		private final float startValue;
		private final float increment;

		MyFloatIla(float startValue, float increment, long length)
		{
		    super(length);
		    
		    this.startValue = startValue;
		    this.increment = increment;
		}

		protected void toArrayImpl(float[] array, int offset,
			long start, int length)
		{
			float v = startValue + increment * start;
			
			for (int i=0 ; i < length ; i++,v+=increment)
			{
				array[offset+i] = v;
			}
		}
    }
}
