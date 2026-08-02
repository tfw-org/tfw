package tfw.tsm.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tfw.test.LogRecordComparator;
import tfw.test.TestHelper;
import tfw.test.ThreadSafeTestHandler;

final class HelloWorldTest {
    private Logger logger;
    private ThreadSafeTestHandler handler;

    @BeforeEach
    public void beforeEach() {
        logger = Logger.getLogger(HelloWorld.class.getName());

        handler = TestHelper.beforeEach(logger);
    }

    @AfterEach
    public void afterEach() {
        TestHelper.afterEach(logger, handler);
    }

    @Test
    void testHelloWorld() {
        final List<LogRecord> expectedLogRecords = new ArrayList<>(
                Arrays.asList(new LogRecord[] {new LogRecord(Level.INFO, HelloWorld.HELLO_WORLD_STRING)}));

        HelloWorld.main(new String[0]);

        List<LogRecord> actualLogRecords = handler.getRecords();

        assertThat(actualLogRecords)
                .usingElementComparator(new LogRecordComparator())
                .containsExactlyElementsOf(expectedLogRecords);
    }
}
