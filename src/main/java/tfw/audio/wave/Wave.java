package tfw.audio.wave;

import java.io.IOException;
import java.util.ArrayList;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class Wave {
    private final WaveChunk[] waveChunks;
    public final ByteIla waveByteData;

    public Wave(final ByteIla byteIla, final int bufferSize) throws IOException {
        Argument.assertNotNull(byteIla, "byteIla");
        waveByteData = byteIla;

        ArrayList<WaveChunk> chunkList = new ArrayList<>();
        WaveRiffChunk waveRiffChunk = new WaveRiffChunk(byteIla, bufferSize);

        chunkList.add(waveRiffChunk);

        ByteIla dataLeft = ByteIlaSegment.create(byteIla, waveRiffChunk.riffChunkData.length());

        while (dataLeft.length() != 0) {
            try {
                WaveFmtChunk waveFmtChunk = new WaveFmtChunk(dataLeft, bufferSize);
                chunkList.add(waveFmtChunk);
                dataLeft = ByteIlaSegment.create(dataLeft, waveFmtChunk.chunkData.length());
                continue;
            } catch (IllegalArgumentException iae) {
            }

            try {
                WaveDataChunk waveDataChunk = new WaveDataChunk(dataLeft, bufferSize);
                chunkList.add(waveDataChunk);
                dataLeft = ByteIlaSegment.create(dataLeft, 8 + waveDataChunk.chunkDataSize);
                continue;
            } catch (IllegalArgumentException iae) {
            }

            WaveUnknownChunk waveUnknownChunk = new WaveUnknownChunk(dataLeft, bufferSize);
            chunkList.add(waveUnknownChunk);
            dataLeft = ByteIlaSegment.create(dataLeft, waveUnknownChunk.unknownChunkData.length());
        }

        waveChunks = chunkList.toArray(new WaveChunk[chunkList.size()]);
    }

    public Wave(WaveChunk[] waveChunks) {
        Argument.assertNotNull(waveChunks, "waveChunks");

        this.waveChunks = waveChunks;
        this.waveByteData = null;
    }

    public WaveChunk[] getChunks() {
        WaveChunk[] wc = new WaveChunk[waveChunks.length];

        System.arraycopy(waveChunks, 0, wc, 0, waveChunks.length);

        return wc;
    }
}
