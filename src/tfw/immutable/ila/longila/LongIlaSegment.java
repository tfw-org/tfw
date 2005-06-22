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

public final class LongIlaSegment
{
    private LongIlaSegment() { }

    public static LongIla create(LongIla instance, long start)
    {
		return create(instance, start, instance.length() - start);
    }

    public static LongIla create(LongIla instance, long start, long length)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(start, 0, "start");
    	Argument.assertNotLessThan(length, 0, "length");
    	Argument.assertNotGreaterThan((start + length), instance.length(),
    		"start + length", "instance.length()");

		return new MyLongIla(instance, start, length);
    }

    private static class MyLongIla extends AbstractLongIla
    {
		private LongIla instance;
		private long start;

		MyLongIla(LongIla instance, long start, long length)
		{
		    super(length);
		    
		    this.instance = instance;
		    this.start = start;
		}

		protected void toArrayImpl(long[] array, int offset,
			long start, int length)
		{
		    instance.toArray(array, offset, this.start + start, length);
		}
    }
}
