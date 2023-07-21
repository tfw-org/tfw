package tfw.audio.wave;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveRiffChunk extends WaveChunk {
    public final int riffType;
    public final ByteIla riffChunkData;

    public WaveRiffChunk(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        super(validateAndGetChunkID(byteIla, bufferSize), getChunkDataSize(byteIla, bufferSize));

        riffType = WaveUtil.intFromSignedFourBytes(byteIla, 8, false, bufferSize);
        riffChunkData = ByteIlaSegment.create(byteIla, 0, 12);

        Argument.assertEquals(chunkID, 0x52494646, "chunkID", "RIFF (0x52494646)");
        Argument.assertEquals(chunkDataSize, byteIla.length() - 8, "chunkDataSize", "byteIla.length() - 8");
        Argument.assertEquals(riffType, 0x57415645, "riffType", "WAVE (0x57415645)");
    }

    private static int validateAndGetChunkID(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(byteIla.length(), 12, "byteIla.length()");

        return WaveUtil.intFromSignedFourBytes(byteIla, 0, false, bufferSize);
    }

    private static long getChunkDataSize(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        return WaveUtil.intFromSignedFourBytes(byteIla, 4, true, bufferSize);
    }
}
