package tfw.tsm.demo;

import net.goui.flogger.testing.LevelClass;
import net.goui.flogger.testing.junit5.FloggerTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class HelloWorldTest {
    @RegisterExtension
    public final FloggerTestExtension logs = FloggerTestExtension.forClassUnderTest(LevelClass.INFO);

    @Test
    void testHelloWorld() {
        HelloWorld.main(new String[0]);

        logs.assertLogs().matchCount().isEqualTo(1);
        logs.assertLog(0).hasLevel(LevelClass.INFO);
        logs.assertLog(0).hasMessageMatching(HelloWorld.HELLO_WORLD_STRING);
    }
}
