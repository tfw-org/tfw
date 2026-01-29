package tfw.audio.demo;

import net.goui.flogger.testing.LevelClass;
import net.goui.flogger.testing.junit5.FloggerTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class AuDemoTest {
    @RegisterExtension
    public final FloggerTestExtension logs = FloggerTestExtension.forClassUnderTest(LevelClass.INFO);

    @Test
    void nullArgsTest() throws Exception {
        AuDemo.main(null);

        logs.assertLogs().withMessageMatching("USAGE: AuDemo auFileName");
    }
}
