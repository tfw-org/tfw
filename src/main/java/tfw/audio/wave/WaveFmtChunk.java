package tfw.audio.wave;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class WaveFmtChunk extends WaveChunk {
    public final int compressionCode;
    public final int numberOfChannels;
    public final int sampleRate;
    public final int averageBytesPerSecond;
    public final int blockAlign;
    public final int significantBitsPerSample;
    public final int extraFormatBytesSize;
    public final ByteIla extraFormatBytes;
    public final ByteIla chunkData;

    public WaveFmtChunk(final ByteIla byteIla, final int bufferSize) throws IOException {
        super(validateAndGetChunkID(byteIla, bufferSize), getChunkDataSize(byteIla, bufferSize));

        Argument.assertEquals(chunkID, 0x666D7420, "chunkID", "'fmt ' 0x666D7420");

        compressionCode = WaveUtil.intFromUnsignedTwoBytes(byteIla, 8, bufferSize);
        numberOfChannels = WaveUtil.intFromUnsignedTwoBytes(byteIla, 10, bufferSize);
        sampleRate = WaveUtil.intFromSignedFourBytes(byteIla, 12, true, bufferSize);
        averageBytesPerSecond = WaveUtil.intFromSignedFourBytes(byteIla, 16, true, bufferSize);
        blockAlign = WaveUtil.intFromUnsignedTwoBytes(byteIla, 20, bufferSize);
        significantBitsPerSample = WaveUtil.intFromUnsignedTwoBytes(byteIla, 22, bufferSize);

        if (chunkDataSize > 16) {
            extraFormatBytesSize = WaveUtil.intFromUnsignedTwoBytes(byteIla, 24, bufferSize);
            extraFormatBytes = ByteIlaSegment.create(byteIla, 26, extraFormatBytesSize);
        } else {
            extraFormatBytesSize = 0;
            extraFormatBytes = null;
        }
        chunkData = ByteIlaSegment.create(byteIla, 0, 8 + chunkDataSize + extraFormatBytesSize);
    }

    private static int validateAndGetChunkID(final ByteIla byteIla, final int bufferSize) throws IOException {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(byteIla.length(), 24, "byteIla.length()");

        return WaveUtil.intFromSignedFourBytes(byteIla, 0, false, bufferSize);
    }

    private static int getChunkDataSize(final ByteIla byteIla, final int bufferSize) throws IOException {
        return WaveUtil.intFromSignedFourBytes(byteIla, 4, true, bufferSize);
    }
}
