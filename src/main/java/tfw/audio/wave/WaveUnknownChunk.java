package tfw.audio.wave;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveUnknownChunk extends WaveChunk {
    public final ByteIla chunkData;
    public final ByteIla unknownChunkData;

    public WaveUnknownChunk(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        super(validateAndGetChunkID(byteIla, bufferSize), getChunkDataSize(byteIla, bufferSize));

        chunkData = ByteIlaSegment.create(byteIla, 8, chunkDataSize);
        unknownChunkData = ByteIlaSegment.create(byteIla, 0, chunkDataSize + 8);
    }

    private static int validateAndGetChunkID(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(byteIla.length(), 8, "byteIla.length()");

        return WaveUtil.intFromSignedFourBytes(byteIla, 0, false, bufferSize);
    }

    private static long getChunkDataSize(final ByteIla byteIla, final int bufferSize) throws DataInvalidException {
        return WaveUtil.intFromSignedFourBytes(byteIla, 4, true, bufferSize);
    }
}
