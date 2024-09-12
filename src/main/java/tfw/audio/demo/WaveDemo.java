package tfw.audio.demo;

import java.io.File;
import tfw.audio.wave.Wave;
import tfw.audio.wave.WaveDataChunk;
import tfw.audio.wave.WaveFmtChunk;
import tfw.audio.wave.WaveRiffChunk;
import tfw.audio.wave.WaveUnknownChunk;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;

public class WaveDemo {
    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        ByteIla byteIla = ByteIlaFromFile.create(file);
        Wave waveFromByteIla = new Wave(byteIla, 1024);

        Object[] chunks = waveFromByteIla.getChunks();

        for (int i = 0; i < chunks.length; i++) {
            if (chunks[i] instanceof WaveRiffChunk) {
                WaveRiffChunk waveRiffChunk = (WaveRiffChunk) chunks[i];

                System.out.println("wRC.chunkID=" + Integer.toHexString(waveRiffChunk.chunkID));
                System.out.println("wRC.chunkDataSize=" + waveRiffChunk.chunkDataSize);
                System.out.println("wRC.riffType=" + Integer.toHexString(waveRiffChunk.riffType));
                System.out.println("wRC.riffChunkData.length()=" + waveRiffChunk.riffChunkData.length());
            } else if (chunks[i] instanceof WaveFmtChunk) {
                WaveFmtChunk waveFmtChunk = (WaveFmtChunk) chunks[i];

                System.out.println("wFC.chunkID=" + Integer.toHexString(waveFmtChunk.chunkID));
                System.out.println("wFC.chunkDataSize=" + waveFmtChunk.chunkDataSize);
                System.out.println("wFC.compressionCode=" + waveFmtChunk.compressionCode);
                System.out.println("wFC.numberOfChannels=" + waveFmtChunk.numberOfChannels);
                System.out.println("wFC.sampleRate=" + waveFmtChunk.sampleRate);
                System.out.println("wFC.averageBytesPerSecond=" + waveFmtChunk.averageBytesPerSecond);
                System.out.println("wFC.blockAlign=" + waveFmtChunk.blockAlign);
                System.out.println("wFC.significantBitsPerSample=" + waveFmtChunk.significantBitsPerSample);
                System.out.println("wFc.extraFormatBytesSize=" + waveFmtChunk.extraFormatBytesSize);
            } else if (chunks[i] instanceof WaveDataChunk) {
                WaveDataChunk waveDataChunk = (WaveDataChunk) chunks[i];

                System.out.println("wDC.chunkID=" + Integer.toHexString(waveDataChunk.chunkID));
                System.out.println("wDC.chunkDataSize=" + waveDataChunk.chunkDataSize);
            } else if (chunks[i] instanceof WaveUnknownChunk) {
                WaveUnknownChunk waveUnknownChunk = (WaveUnknownChunk) chunks[i];

                System.out.println("wUC.chunkID=" + Integer.toHexString(waveUnknownChunk.chunkID));
                System.out.println("wUC.chunkDataSize=" + waveUnknownChunk.chunkDataSize);
            }
        }
    }
}
