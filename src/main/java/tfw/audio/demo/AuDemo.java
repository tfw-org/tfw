package tfw.audio.demo;

import com.google.common.flogger.FluentLogger;
import java.io.File;
import java.io.IOException;
import tfw.audio.au.Au;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromFile;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaUtil;

public class AuDemo {
    private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

    private AuDemo() {}

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 1) {
            LOGGER.atInfo().log("USAGE: AuDemo auFileName");

            return;
        }

        File file = new File(args[0]);
        ByteIla byteIla = ByteIlaFromFile.create(file);
        Au auFileFormat = new Au(byteIla);

        LOGGER.atInfo().log("filename = %s", args[0]);
        LOGGER.atInfo().log("file.length = %d", file.length());
        LOGGER.atInfo().log("magicNumber = %x", auFileFormat.magicNumber);
        LOGGER.atInfo().log("offset = %d", auFileFormat.offset);
        LOGGER.atInfo().log("dataSize = %d", auFileFormat.dataSize);
        LOGGER.atInfo().log("encoding = %d", auFileFormat.encoding);
        LOGGER.atInfo().log("sampleRate = %d", auFileFormat.sampleRate);
        LOGGER.atInfo().log("numberOfChannels = %d", auFileFormat.numberOfChannels);
        LOGGER.atInfo().log("annotation = %s", new String(ByteIlaUtil.toArray(auFileFormat.annotation)));
        LOGGER.atInfo().log("data.length = %d", auFileFormat.audioData.length());

        DoubleIla normalizedData = NormalizedDoubleIlaFromAuAudioData.create(
                auFileFormat.audioData, auFileFormat.magicNumber, auFileFormat.encoding, 1000);

        LOGGER.atInfo().log("normalizedData.length = %d", normalizedData.length());

        if (normalizedData != null) {
            double[] d = DoubleIlaUtil.toArray(normalizedData, 0, 10);
            for (int i = 0; i < d.length; i++) {
                LOGGER.atInfo().log("  nD[%d]=%d", i, d[i]);
            }
        }
    }
}
