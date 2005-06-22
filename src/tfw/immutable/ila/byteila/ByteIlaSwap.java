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

public class ByteIlaSwap
{
    private ByteIlaSwap() {}

    public static ByteIla create(ByteIla byteIla, int bytesToSwap)
    {
		if(byteIla == null)
	    	throw new IllegalArgumentException
				("byteIla == null not allowed");

		return new MyByteIla(byteIla, bytesToSwap);
    }

    private static class MyByteIla extends AbstractByteIla
    {
		private final ByteIla byteIla;
		private final int bytesToSwap;

		MyByteIla(ByteIla byteIla, int bytesToSwap)
		{
		    super(byteIla.length() - (byteIla.length() % bytesToSwap));
		    
		    this.byteIla = byteIla;
		    this.bytesToSwap = bytesToSwap;
		}

		protected void toArrayImpl(byte[] array, int offset,
			long start, int length)
		{
			long end = start + length - 1;
			long blockStart = start - (start % bytesToSwap);
			long blockEnd = end + bytesToSwap - (end % bytesToSwap) - 1;
			int blockLength = (int)(blockEnd - blockStart + 1);
		    ByteIlaIterator bii = new ByteIlaIterator(
		    	ByteIlaSegment.create(byteIla, blockStart, blockLength));
		    byte[] bytes = new byte[bytesToSwap];

			for (int j=bytesToSwap-1 ; j >= 0 ; j--)
			{
				bytes[j] = bii.next();
			}
		    int bs = (int)(start % bytesToSwap);
		    
		    for (int l=0 ; l < length ; l++,bs++)
		    {
		    	if (bs == bytesToSwap)
		    	{
		    		for (int j=bytesToSwap-1 ; j >= 0 ; j--)
		    		{
		    			bytes[j] = bii.next();
		    		}
		    		bs = 0;
		    	}

		    	array[offset+l] = bytes[bs];
		    }
		}
		
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			
			sb.append("ByteIlaSwap: length=");
			sb.append(length);
			sb.append(" bytesToSwap=");
			sb.append(bytesToSwap);
			sb.append("\n");
			sb.append(byteIla.toString());
			
			return(sb.toString());
		}
    }
}
