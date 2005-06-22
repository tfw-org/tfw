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
package tfw.immutable.ila.charila;

import tfw.check.Argument;

public final class CharIlaSegment
{
    private CharIlaSegment() { }

    public static CharIla create(CharIla instance, long start)
    {
		return create(instance, start, instance.length() - start);
    }

    public static CharIla create(CharIla instance, long start, long length)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(start, 0, "start");
    	Argument.assertNotLessThan(length, 0, "length");
    	Argument.assertNotGreaterThan((start + length), instance.length(),
    		"start + length", "instance.length()");

		return new MyCharIla(instance, start, length);
    }

    private static class MyCharIla extends AbstractCharIla
    {
		private CharIla instance;
		private long start;

		MyCharIla(CharIla instance, long start, long length)
		{
		    super(length);
		    
		    this.instance = instance;
		    this.start = start;
		}

		protected void toArrayImpl(char[] array, int offset,
			long start, int length)
		{
		    instance.toArray(array, offset, this.start + start, length);
		}
    }
}
