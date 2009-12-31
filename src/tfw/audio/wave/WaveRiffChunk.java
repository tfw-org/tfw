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

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveRiffChunk extends WaveChunk
{
	public final int riffType;
	public final ByteIla riffChunkData;
	
	public WaveRiffChunk(ByteIla byteIla) throws DataInvalidException
	{
		super(validateAndGetChunkID(byteIla), getChunkDataSize(byteIla));
		
		riffType = WaveUtil.intFromSignedFourBytes(byteIla, 8, false);
		riffChunkData = ByteIlaSegment.create(byteIla, 0, 12);
		
		Argument.assertEquals(chunkID, 0x52494646, "chunkID", "RIFF (0x52494646)");
		Argument.assertEquals(chunkDataSize, byteIla.length() - 8,
			"chunkDataSize", "byteIla.length() - 8");
		Argument.assertEquals(riffType, 0x57415645, "riffType", "WAVE (0x57415645)");
	}
	
	private static int validateAndGetChunkID(ByteIla byteIla)
		throws DataInvalidException
	{
		Argument.assertNotNull(byteIla, "byteIla");
		Argument.assertNotLessThan(byteIla.length(), 12, "byteIla.length()");
		
		return(WaveUtil.intFromSignedFourBytes(byteIla, 0, false));
	}
	
	private static long getChunkDataSize(ByteIla byteIla)
		throws DataInvalidException
	{
		return(WaveUtil.intFromSignedFourBytes(byteIla, 4, true));
	}
}
