package tfw.audio.wave;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveFmtChunk extends WaveChunk
{
	public final int compressionCode;
	public final int numberOfChannels;
	public final int sampleRate;
	public final int averageBytesPerSecond;
	public final int blockAlign;
	public final int significantBitsPerSample;
	public final int extraFormatBytesSize;
	public final ByteIla extraFormatBytes;
	public final ByteIla chunkData;
	
	public WaveFmtChunk(ByteIla byteIla) throws DataInvalidException
	{
		super(validateAndGetChunkID(byteIla), getChunkDataSize(byteIla));
		
		Argument.assertEquals(chunkID, 0x666D7420, "chunkID", "'fmt ' 0x666D7420");
		
		compressionCode = WaveUtil.intFromUnsignedTwoBytes(byteIla, 8);
		numberOfChannels = WaveUtil.intFromUnsignedTwoBytes(byteIla, 10);
		sampleRate = WaveUtil.intFromSignedFourBytes(byteIla, 12, true);
		averageBytesPerSecond = WaveUtil.intFromSignedFourBytes(byteIla, 16, true);
		blockAlign = WaveUtil.intFromUnsignedTwoBytes(byteIla, 20);
		significantBitsPerSample = WaveUtil.intFromUnsignedTwoBytes(byteIla, 22);
		
		if (chunkDataSize > 16)
		{
			extraFormatBytesSize = WaveUtil.intFromUnsignedTwoBytes(byteIla, 24);
			extraFormatBytes = ByteIlaSegment.create(byteIla, 26, extraFormatBytesSize);
		}
		else
		{
			extraFormatBytesSize = 0;
			extraFormatBytes = null;
		}
		chunkData = ByteIlaSegment.create(byteIla, 0, 8 + chunkDataSize + extraFormatBytesSize);
	}
	
	private static int validateAndGetChunkID(ByteIla byteIla)
		throws DataInvalidException
	{
		Argument.assertNotNull(byteIla, "byteIla");
		Argument.assertNotLessThan(byteIla.length(), 24, "byteIla.length()");

		return(WaveUtil.intFromSignedFourBytes(byteIla, 0, false));
	}
	
	private static int getChunkDataSize(ByteIla byteIla)
		throws DataInvalidException
	{
		return(WaveUtil.intFromSignedFourBytes(byteIla, 4, true));
	}
}