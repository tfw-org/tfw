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

import java.util.ArrayList;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class Wave
{
	private final WaveChunk[] waveChunks;
	public final ByteIla waveByteData;
	
	public Wave(ByteIla byteIla) throws DataInvalidException
	{
		Argument.assertNotNull(byteIla, "byteIla");
		waveByteData = byteIla;
		
		ArrayList chunkList = new ArrayList();
		WaveRiffChunk waveRiffChunk = null;
		
		try
		{
			waveRiffChunk = new WaveRiffChunk(byteIla);
		}
		catch(DataInvalidException die)
		{
			throw new IllegalArgumentException("Not a WAVE File!");
		}
		
		chunkList.add(waveRiffChunk);
		
		ByteIla dataLeft = ByteIlaSegment.create(byteIla, waveRiffChunk.riffChunkData.length());
		
		while(dataLeft.length() != 0)
		{
			try
			{
				WaveFmtChunk waveFmtChunk = new WaveFmtChunk(dataLeft);
				chunkList.add(waveFmtChunk);
				dataLeft = ByteIlaSegment.create(dataLeft, waveFmtChunk.chunkData.length());
				continue;
			}
			catch(IllegalArgumentException iae) {}
			
			try
			{
				WaveDataChunk waveDataChunk = new WaveDataChunk(dataLeft);
				chunkList.add(waveDataChunk);
				dataLeft = ByteIlaSegment.create(dataLeft, 8 + waveDataChunk.chunkDataSize);
				continue;
			}
			catch(IllegalArgumentException iae) {}
			
			WaveUnknownChunk waveUnknownChunk = new WaveUnknownChunk(dataLeft);
			chunkList.add(waveUnknownChunk);
			dataLeft = ByteIlaSegment.create(dataLeft, waveUnknownChunk.unknownChunkData.length());
		}
		
		waveChunks = (WaveChunk[])chunkList.toArray(new WaveChunk[chunkList.size()]);
	}
	
	public Wave(WaveChunk[] waveChunks)
	{
		Argument.assertNotNull(waveChunks, "waveChunks");
		
		this.waveChunks = waveChunks;
		this.waveByteData = null;
	}
	
	public WaveChunk[] getChunks()
	{
		return((WaveChunk[])waveChunks.clone());
	}
}