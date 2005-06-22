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
package tfw.immutable.ilm.intilm;

import tfw.check.Argument;

public final class IntIlmSegment
{
    private IntIlmSegment() { }

    public static IntIlm create(IntIlm instance, long rowStart, long columnStart)
    {
		return create(instance, rowStart, columnStart,
			instance.width() - columnStart, instance.height() - rowStart);
    }

    public static IntIlm create(IntIlm instance, long rowStart,
    	long columnStart, long width, long height)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(rowStart, 0, "rowStart");
    	Argument.assertNotLessThan(columnStart, 0, "columnStart");
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	Argument.assertNotGreaterThan((rowStart + height), instance.height(),
    		"rowStart + height", "instance.height()");
    	Argument.assertNotGreaterThan((columnStart + width), instance.width(),
    		"columnStart + width", "instance.width()");

		return new MyIntIlm(instance, rowStart, columnStart, width, height);
    }

    private static class MyIntIlm extends AbstractIntIlm
    {
		private final IntIlm instance;
		private final long rowStart;
		private final long columnStart;

		MyIntIlm(IntIlm instance, long rowStart, long columnStart,
			long width, long height)
		{
		    super(width, height);
		    
		    this.instance = instance;
		    this.rowStart = rowStart;
		    this.columnStart = columnStart;
		}

		protected void toArrayImpl(int[][] array, int rowOffset, int columnOffset,
			long rowStart, long columnStart, int width, int height)
		{
			instance.toArray(array, rowOffset, columnOffset, this.rowStart + rowStart,
				this.columnStart + columnStart, width, height);
		}
    }
}
