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
package tfw.immutable.ila.byteila;

import tfw.check.Argument;

public final class ByteIlaMutate
{
    private ByteIlaMutate() {}

    public static ByteIla create(ByteIla instance, long index,
		byte value)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertLessThan(index, instance.length(),
    		"index", "instance.length()");

		return new MyByteIla(instance, index, value);
    }

    private static class MyByteIla extends AbstractByteIla
    {
		private ByteIla instance;
		private long index;
		private byte value;

		MyByteIla(ByteIla instance, long index, byte value)
		{
		    super(instance.length());
		    
		    this.instance = instance;
		    this.index = index;
		    this.value = value;
		}

		protected void toArrayImpl(byte[] array, int offset,
			long start, int length)
		{
			long startPlusLength = start + length;
			
			if(index < start || index >= startPlusLength)
			{
				instance.toArray(array, offset, start, length);
			}
			else
			{
				long indexMinusStart = index - start;
				if(index > start)
				{
				    instance.toArray(array, offset, start,
						(int) indexMinusStart);
				}
				array[offset + (int) indexMinusStart] = value;
				if(index <= startPlusLength)
				{
					instance.toArray(array, (int)
						(offset + indexMinusStart + 1),
						index + 1,
						(int) (length - indexMinusStart - 1));
				}
	    	}
		}
    }
}