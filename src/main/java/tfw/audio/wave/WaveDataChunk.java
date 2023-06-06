package tfw.audio.wave;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveDataChunk extends WaveChunk {
    public final ByteIla chunkData;
    public final ByteIla dataChunkData;

    public WaveDataChunk(ByteIla byteIla) throws DataInvalidException {
        super(validateAndGetChunkID(byteIla), getChunkDataSize(byteIla));

        chunkData = ByteIlaSegment.create(byteIla, 8, chunkDataSize);
        dataChunkData = ByteIlaSegment.create(byteIla, 0, 8 + chunkDataSize);

        Argument.assertEquals(chunkID, 0x64617461, "chunkID", "'data' (0x64617461)");
    }

    private static int validateAndGetChunkID(ByteIla byteIla) throws DataInvalidException {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(byteIla.length(), 8, "byteIla.length()");

        return (WaveUtil.intFromSignedFourBytes(byteIla, 0, false));
    }

    private static long getChunkDataSize(ByteIla byteIla) throws DataInvalidException {
        return (WaveUtil.intFromSignedFourBytes(byteIla, 4, true));
    }
}
