/*
 * The Framework Project
 * Copyright (C) 2006 Anonymous
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
 * without even the implied warranty of
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
package tfw.audio.wave;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.byteila.ByteIlaSwap;

public final class WaveUtil
{
	private WaveUtil() {}
	
	public static int intFromSignedFourBytes(ByteIla byteIla, long offset,
		boolean swap) throws DataInvalidException
	{
		if (swap)
		{
			byteIla = ByteIlaSwap.create(byteIla, 4);
		}
		byte[] b = ByteIlaSegment.create(byteIla, offset, 4).toArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		DataInputStream dis = new DataInputStream(bais);
		
		try
		{
			return(dis.readInt());
		}
		catch(IOException ioe)
		{
			throw new DataInvalidException("intFromSignedFourBytes", ioe);
		}
	}
	
	public static int intFromUnsignedTwoBytes(ByteIla byteIla, long offset)
		throws DataInvalidException
	{
		byteIla = ByteIlaSwap.create(byteIla, 2);
		byte[] b = ByteIlaSegment.create(byteIla, offset, 2).toArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		DataInputStream dis = new DataInputStream(bais);
		
		try
		{
			return(dis.readUnsignedShort());
		}
		catch(IOException ioe)
		{
			throw new DataInvalidException("intFromUnsignedTwoBytes", ioe);
		}
	}
}
