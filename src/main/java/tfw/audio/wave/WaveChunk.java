package tfw.audio.wave;

public class WaveChunk {
    public final int chunkID;
    public final long chunkDataSize;

    public WaveChunk(int chunkID, long chunkDataSize) {
        this.chunkID = chunkID;
        this.chunkDataSize = chunkDataSize;
    }
}
