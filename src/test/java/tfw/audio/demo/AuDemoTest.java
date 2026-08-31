package tfw.audio.demo;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.goui.flogger.testing.LevelClass;
import net.goui.flogger.testing.junit5.FloggerTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

class AuDemoTest {
    @RegisterExtension
    public final FloggerTestExtension logs = FloggerTestExtension.forClassUnderTest(LevelClass.INFO);

    @Test
    void nullArgsTest() throws Exception {
        AuDemo.main(null);

        logs.assertLogs().matchCount().isEqualTo(1);
        logs.assertLog(0).hasMessageMatching("USAGE: AuDemo auFileName");
    }

    @Test
    void emptyArgsTest() throws Exception {
        AuDemo.main(new String[0]);

        logs.assertLogs().matchCount().isEqualTo(1);
        logs.assertLog(0).hasMessageMatching("USAGE: AuDemo auFileName");
    }

    @Test
    void fullTest(@TempDir Path tempDir) throws Exception {
        final Path testFile = tempDir.resolve("test.au");
        final String testFilePath = testFile.toFile().getAbsolutePath();

        Files.write(testFile, createTestBytes());
        AuDemo.main(new String[] {testFilePath});

        logs.assertLogs().matchCount().isEqualTo(11);
        logs.assertLog(0).hasMessageMatching("/tmp/junit-\\d+/test.au");
        logs.assertLog(1).hasMessageMatching("file.length = 284");
        logs.assertLog(2).hasMessageMatching("magicNumber = 2E736E64");
        logs.assertLog(3).hasMessageMatching("offset = 24");
        logs.assertLog(4).hasMessageMatching("dataSize = 256");
        logs.assertLog(5).hasMessageMatching("encoding = 27");
        logs.assertLog(6).hasMessageMatching("sampleRate = 8000");
        logs.assertLog(7).hasMessageMatching("numberOfChannels = 1");
        logs.assertLog(8).hasMessageMatching("annotation = ");
        logs.assertLog(9).hasMessageMatching("data.length = 260");
        logs.assertLog(10).hasMessageMatching("normalizedData.length = 260");
    }

    private static byte[] createTestBytes() {
        final byte[] audioBytes = new byte[256];

        for (int i = 0; i < audioBytes.length; i++) {
            audioBytes[i] = (byte) (i & 0xFF);
        }

        final ByteBuffer byteBuffer = ByteBuffer.allocate(1000);

        byteBuffer.putInt(0x2E736E64);
        byteBuffer.putInt(24);
        byteBuffer.putInt(audioBytes.length);
        byteBuffer.putInt(27);
        byteBuffer.putInt(8000);
        byteBuffer.putInt(1);
        byteBuffer.putInt(0);
        byteBuffer.put(audioBytes);

        final byte[] bytes = new byte[byteBuffer.position()];

        byteBuffer.position(0);
        byteBuffer.get(bytes);

        return bytes;
    }
}
