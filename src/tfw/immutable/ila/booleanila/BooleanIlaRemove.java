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
package tfw.immutable.ila.booleanila;

import tfw.check.Argument;

public final class BooleanIlaRemove
{
    private BooleanIlaRemove() {}

    public static BooleanIla create(BooleanIla instance, long index)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertLessThan(index, instance.length(),
    		"index", "instance.length()");

		return new MyBooleanIla(instance, index);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
    {
		private BooleanIla instance;
		private long index;

		MyBooleanIla(BooleanIla instance, long index)
		{
		    super(instance.length() - 1);
		    
		    this.instance = instance;
		    this.index = index;
		}

		protected void toArrayImpl(boolean[] array, int offset,
			long start, int length)
		{
		    if(index <= start)
		    {
				instance.toArray(array, offset, start + 1, length);
		    }
		    else if(index >= start + length)
		    {
				instance.toArray(array, offset, start, length);
		    }
		    else
		    {
				long indexMinusStart = index - start;
				instance.toArray(array, offset, start, (int) indexMinusStart);
				instance.toArray(array, (int) (offset + indexMinusStart),
					index + 1, (int) (length - indexMinusStart));
	    	}
		}
    }
}